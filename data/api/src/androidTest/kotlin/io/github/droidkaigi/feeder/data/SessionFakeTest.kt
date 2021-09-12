package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.fakeTimetableContents
import kotlin.test.Test
import kotlin.test.assertEquals

class SessionFakeTest {
    @Test
    fun assertSameSessionFakeAndApiFake() = runBlocking {
        val sessionContents = fakeTimetableApi()
            .fetch()
        assertEquals(fakeTimetableContents(), sessionContents)
    }
}
