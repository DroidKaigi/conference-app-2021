package io.github.droidkaigi.feeder.data

import kotlin.test.AfterTest
import platform.Foundation.NSUserDefaults

class DataStoreUserDataStoreTest {

    @AfterTest
    fun teardown() {
        val userDefaults = NSUserDefaults(TEST_SUITE_NAME)
        userDefaults.dictionaryRepresentation().keys.forEach {
            userDefaults.removeObjectForKey(it as String)
        }
    }

//    @Test
//    fun testFavoriteReadAndWrite() = runBlocking {
//        // give
//        val dataStore = DataStoreUserDataStore(TEST_SUITE_NAME)
//
//        // when
//        dataStore.addFavorite("1")
//        dataStore.addFavorite("2")
//        dataStore.addFavorite("3")
//
//        // then
//        assertEquals(dataStore.favorites().first(), setOf("1", "2", "3"))
//    }
//
//    @Test
//    fun testAuthenticatedReadAndWrite() {
//        // give
//        val dataStore = DataStoreUserDataStore(TEST_SUITE_NAME)
//
//        // when
//        runBlocking {
//            dataStore.setAuthenticated(false)
//            dataStore.setAuthenticated(true)
//        }
//
//        // then
//        runBlocking {
//            assertEquals(dataStore.isAuthenticated().first(), true)
//        }
//    }
//
//    @Test
//    fun testDeviceIdReadAndWrite() {
//        // give
//        val dataStore = DataStoreUserDataStore(TEST_SUITE_NAME)
//
//        // when
//        runBlocking {
//            dataStore.setDeviceId("----deviceid----")
//        }
//
//        // then
//        runBlocking {
//            assertEquals(dataStore.deviceId().first(), "----deviceid----")
//        }
//    }

    companion object {
        private const val TEST_SUITE_NAME =
            "io.github.droidkaigi.feeder.data.DataStoreUserDataStoreTest"
    }
}
