package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.fakeSessionContents
import kotlin.test.Test
import kotlin.test.assertEquals

class SessionFakeTest {
    @Test
    fun assertSameSessionFakeAndApiFake() = runBlocking {
        val sessionContents = fakeSessionApi()
            .fetch()
        assertEquals(fakeSessionContents(), sessionContents)
    }
}
