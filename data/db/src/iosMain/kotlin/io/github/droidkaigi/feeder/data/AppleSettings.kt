package io.github.droidkaigi.feeder.data

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SettingsListener
import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze
import platform.Foundation.NSNotification
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSUserDefaults
import platform.Foundation.NSUserDefaultsDidChangeNotification
import platform.darwin.NSObjectProtocol

/*
 Reference:
 https://github.com/russhwolf/multiplatform-settings/blob/master/multiplatform-settings/src/appleMain/kotlin/com/russhwolf/settings/AppleSettings.kt
 *
 */

@OptIn(ExperimentalSettingsApi::class)
class AppleSettings constructor(
    private val delegate: NSUserDefaults,
    private val useFrozenListeners: Boolean = false
) : ObservableSettings {
    init {
        // We hold no state at the Kotlin level, so shouldn't run into freeze issues. If we know we're already frozen
        // then if listeners freeze things it'll be less surprising
        freeze()
    }

    // Secondary constructor instead of default parameter for backward-compatibility
    constructor(delegate: NSUserDefaults) : this(delegate, useFrozenListeners = false)

    /**
     * A factory that can produce [Settings] instances.
     *
     * This class can only be instantiated via a platform-specific constructor. It's purpose is so that `Settings`
     * objects can be created in common code, so that the only platform-specific behavior necessary in order to use
     * multiple `Settings` objects is the one-time creation of a single `Factory`.
     *
     * On the iOS and macOS platforms, this class creates `Settings` objects backed by [NSUserDefaults].
     */
    class Factory : Settings.Factory {

        /**
         * Creates a [Settings] object associated with the provided [name].
         *
         * Multiple `Settings` instances created with the same `name` parameter will be backed by the same persistent
         * data, while distinct `name`s will use different data. If `name` is `null` then a platform-specific default
         * will be used.
         *
         * On the iOS and macOS platforms, this is implemented by calling [NSUserDefaults.init] and passing [name]. If `name` is
         * `null` then [NSUserDefaults.standardUserDefaults] will be used instead.
         */
        override fun create(name: String?): AppleSettings {
            val delegate = if (name == null) {
                NSUserDefaults.standardUserDefaults
            } else {
                NSUserDefaults(suiteName = name)
            }
            return AppleSettings(delegate)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override val keys: Set<String> get() = delegate.dictionaryRepresentation().keys as Set<String>
    override val size: Int get() = delegate.dictionaryRepresentation().keys.count()

    override fun clear() {
        for (key in delegate.dictionaryRepresentation().keys) {
            remove(key as String)
        }
    }

    override fun remove(key: String): Unit = delegate.removeObjectForKey(key)

    override fun hasKey(key: String): Boolean = delegate.objectForKey(key) != null

    override fun putInt(key: String, value: Int): Unit = delegate.setInteger(value.toLong(), key)

    override fun getInt(key: String, defaultValue: Int): Int =
        if (hasKey(key)) delegate.integerForKey(key).toInt() else defaultValue

    override fun getIntOrNull(key: String): Int? =
        if (hasKey(key)) delegate.integerForKey(key).toInt() else null

    override fun putLong(key: String, value: Long): Unit = delegate.setInteger(value, key)

    override fun getLong(key: String, defaultValue: Long): Long =
        if (hasKey(key)) delegate.integerForKey(key) else defaultValue

    override fun getLongOrNull(key: String): Long? =
        if (hasKey(key)) delegate.integerForKey(key) else null

    override fun putString(key: String, value: String): Unit = delegate.setObject(value, key)

    override fun getString(key: String, defaultValue: String): String =
        delegate.stringForKey(key) ?: defaultValue

    override fun getStringOrNull(key: String): String? = delegate.stringForKey(key)

    override fun putFloat(key: String, value: Float): Unit = delegate.setFloat(value, key)

    override fun getFloat(key: String, defaultValue: Float): Float =
        if (hasKey(key)) delegate.floatForKey(key) else defaultValue

    override fun getFloatOrNull(key: String): Float? =
        if (hasKey(key)) delegate.floatForKey(key) else null

    override fun putDouble(key: String, value: Double): Unit = delegate.setDouble(value, key)

    override fun getDouble(key: String, defaultValue: Double): Double =
        if (hasKey(key)) delegate.doubleForKey(key) else defaultValue

    override fun getDoubleOrNull(key: String): Double? =
        if (hasKey(key)) delegate.doubleForKey(key) else null

    override fun putBoolean(key: String, value: Boolean): Unit = delegate.setBool(value, key)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        if (hasKey(key)) delegate.boolForKey(key) else defaultValue

    override fun getBooleanOrNull(key: String): Boolean? =
        if (hasKey(key)) delegate.boolForKey(key) else null

    @ExperimentalSettingsApi
    override fun addListener(key: String, callback: () -> Unit): SettingsListener {
        val (block, previousValue) = if (useFrozenListeners) {
            createBackgroundListener(key, callback)
        } else {
            createMainThreadListener(key, callback)
        }
        val observer = NSNotificationCenter.defaultCenter.addObserverForName(
            name = NSUserDefaultsDidChangeNotification,
            `object` = delegate,
            queue = null,
            usingBlock = block
        )
        return Listener(observer, previousValue)
    }

    private fun createMainThreadListener(
        key: String,
        callback: () -> Unit
    ): Pair<(NSNotification?) -> Unit, AtomicReference<Any?>?> {
        var previousValue = delegate.objectForKey(key)

        return { _: NSNotification? ->
            /*
             We'll get called here on any update to the underlying NSUserDefaults delegate. We use a cache to determine
             whether the value at this listener's key changed before calling the user-supplied callback.
             */
            val current = delegate.objectForKey(key)
            if (previousValue != current) {
                callback.invoke()
                previousValue = current
            }
        } to null
    }

    private fun createBackgroundListener(
        key: String,
        callback: () -> Unit
    ): Pair<(NSNotification?) -> Unit, AtomicReference<Any?>?> {
        val previousValue: AtomicReference<Any?> =
            AtomicReference(delegate.objectForKey(key).freeze())

        return { _: NSNotification? ->
            /*
             We'll get called here on any update to the underlying NSUserDefaults delegate. We use a cache to determine
             whether the value at this listener's key changed before calling the user-supplied callback.
             */
            val current = delegate.objectForKey(key).freeze()
            if (previousValue.value != current) {
                callback.invoke()
                previousValue.value = current
            }
        }.freeze() to previousValue
    }

    /**
     * A handle to a listener instance created in [addListener] so it can be passed to [removeListener]
     *
     * On the iOS and macOS platforms, this is a wrapper around the object returned by [NSNotificationCenter.addObserverForName]
     */
    @ExperimentalSettingsApi
    class Listener internal constructor(
        private val delegate: NSObjectProtocol,
        private val previousValue: AtomicReference<Any?>?
    ) : SettingsListener {
        override fun deactivate() {
            NSNotificationCenter.defaultCenter.removeObserver(delegate)
            previousValue?.value = null
        }
    }
}
