package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.fakeSessionContents
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class SessionFakeTest {
    @Test
    fun generate() = runBlocking {
        val sessionContents = fakeSessionApi()
            .fetch()
        assertEquals(fakeSessionContents(), sessionContents)
    }
}

// SessionContents(timetableSlots=[Session(title=MultiLangText(jaTitle=ウェルカムトーク, enTitle=Welcome Talk), speakers=[]), Session(title=MultiLangText(jaTitle=DroidKaigiのアプリのアーキテクチャ, enTitle=DroidKaigi App Architecture), speakers=[Speaker(name=taka, iconUrl=https://github.com/takahirom.png), Speaker(name=ry, iconUrl=https://github.com/ry-itto.png)]), Session(title=MultiLangText(jaTitle=Closing, enTitle=Closing), speakers=[])])> but was:<
// SessionContents(timetableSlots=[Special(title=MultiLangText(jaTitle=ウェルカムトーク, enTitle=Welcome Talk), speakers=[]), Session(title=MultiLangText(jaTitle=DroidKaigiのアプリのアーキテクチャ, enTitle=DroidKaigi App Architecture), speakers=[Speaker(name=taka, iconUrl=https://github.com/takahirom.png), Speaker(name=ry, iconUrl=https://github.com/ry-itto.png)]), Special(title=MultiLangText(jaTitle=Closing, enTitle=Closing), speakers=[])])
