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
