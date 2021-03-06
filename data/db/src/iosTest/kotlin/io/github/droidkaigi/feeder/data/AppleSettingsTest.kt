package io.github.droidkaigi.feeder.data

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import platform.Foundation.NSUserDefaults

class AppleSettingsTest {

    private val userDefaults = NSUserDefaults(
        suiteName = "io.github.droidkaigi.feeder.data.AppleSettingsTest"
    )

    @AfterTest
    fun teardown() {
        userDefaults.dictionaryRepresentation().keys.forEach {
            userDefaults.removeObjectForKey(it as String)
        }
    }

    @Test
    fun testIntegerReadAndWrite() {
        // give
        val key = "integer-key"
        val appleSettings = AppleSettings(userDefaults)

        // when
        appleSettings.putInt(key, 22)

        // then
        assertEquals(appleSettings.getInt(key), 22)
    }

    @Test
    fun testStringReadAndWrite() {
        // give
        val key = "string-key"
        val appleSettings = AppleSettings(userDefaults)

        // when
        appleSettings.putString(key, "abc")

        // then
        assertEquals(appleSettings.getString(key), "abc")
    }
}
