package io.github.droidkaigi.feeder

data class SessionContents(
    val timetableSlots: List<TimetableSlot>,
)

fun fakeSessionContents(): SessionContents = SessionContents(
    timetableSlots = listOf(
        TimetableSlot.Special(
            MultiLangText("ウェルカムトーク", "Welcome Talk"),
        ),
        TimetableSlot.Session(
            MultiLangText("DroidKaigiのアプリのアーキテクチャ", "DroidKaigi App Architecture"),
            speakers = listOf(
                Speaker("taka", "https://github.com/takahirom.png"),
                Speaker("ry", "https://github.com/ry-itto.png")
            )
        ),
        TimetableSlot.Special(
            MultiLangText("Closing", "Closing"),
        ),
    )
)

// SessionContents(timetableSlots=[Session(title=MultiLangText(jaTitle=ウェルカムトーitle=Welcome Talk), speakers=[]), Session(title=MultiLangText(jaTitle=DroidKaigiのアプリのアーキテクチャ, enTitle=DroidKaitecture), speakers=[Speaker(name=taka, iconUrl=https://github.com/takahirom.png), Speaker(name=ry, iconUrl=https://github.com/ry-itto.png)]), Session(title=MultiLangText(jaTitle=Closing, enTitle=Closing), speakers=[])])> but was:<
// SessionContents(timetableSlots=[Special(title=MultiLangText(jaTitle=ウェルカムトーク, enTitle=Welcome Talk), speakers=[]), Session(ttiLangText(jaTitle=DroidKaigiのアプリのアーキテクチャ, enTitle=DroidKaigi App Architecture), speakers=[Speaker(name=taka,ps://github.com/takahirom.png), Speaker(name=ry, iconUrl=https://github.com/ry-itto.png)]), Special(title=MultiLangText(jaTitle=Closing, enTitle=Closing), speakers=[])])
