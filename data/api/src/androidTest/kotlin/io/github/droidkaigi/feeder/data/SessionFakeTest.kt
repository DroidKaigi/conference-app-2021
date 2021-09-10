package io.github.droidkaigi.feeder.data

import kotlin.test.Test

class SessionFakeTest {
    @Test
    fun generate() = runBlocking {
        val feedList = fakeSessionApi()
            .fetch()
        println(feedList)
    }
}
