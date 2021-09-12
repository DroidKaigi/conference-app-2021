package io.github.droidkaigi.feeder

data class SessionContents(
    val timetableItems: List<TimetableItem>,
)

fun fakeSessionContents(): SessionContents = SessionContents(
    timetableItems = listOf(
        TimetableItem.Special(
            MultiLangText("ウェルカムトーク", "Welcome Talk"),
        ),
        TimetableItem.Session(
            MultiLangText("DroidKaigiのアプリのアーキテクチャ", "DroidKaigi App Architecture"),
            speakers = listOf(
                Speaker("taka", "https://github.com/takahirom.png"),
                Speaker("ry", "https://github.com/ry-itto.png")
            )
        ),
        TimetableItem.Special(
            MultiLangText("Closing", "Closing"),
        ),
    )
)
