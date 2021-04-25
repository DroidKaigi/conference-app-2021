package io.github.droidkaigi.feeder.data

import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import platform.Foundation.NSUserDefaults

class DataStoreUserDataStore(name: String? = null) : UserDataStore() {

    private val appleSetting: AppleSettings

    init {
        val userDefaults = if (name != null) {
            NSUserDefaults(name)
        } else {
            NSUserDefaults.standardUserDefaults
        }

        appleSetting = AppleSettings(userDefaults, useFrozenListeners = true)
    }

    override val flowSettings = appleSetting.toFlowSettings()
}
