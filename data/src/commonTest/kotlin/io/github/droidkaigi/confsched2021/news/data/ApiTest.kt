package io.github.droidkaigi.confsched2021.news.data

import kotlin.test.Test
import kotlin.test.assertTrue

class ApiTest {
    @Test
    fun test() {
        runBlocking {
            assertTrue(NewsApi().fetch().isNotEmpty())
        }
    }
}