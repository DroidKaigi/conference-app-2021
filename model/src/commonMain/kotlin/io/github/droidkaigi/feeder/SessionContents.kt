package io.github.droidkaigi.feeder

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

data class SessionContents(
    val timetableItems: List<TimetableItem>,
)

fun fakeSessionContents(): SessionContents = SessionContents(
    timetableItems = listOf(
        TimetableItem.Special(
            MultiLangText("ウェルカムトーク", "Welcome Talk"),
            startsAt = LocalDateTime.parse("2021-10-20T10:00:00")
                .toInstant(TimeZone.of("UTC+9")),
            endsAt = LocalDateTime.parse("2021-10-20T10:20:00")
                .toInstant(TimeZone.of("UTC+9"))
        ),
        TimetableItem.Session(
            MultiLangText("DroidKaigiのアプリのアーキテクチャ", "DroidKaigi App Architecture"),
            startsAt = LocalDateTime.parse("2021-10-20T10:30:00")
                .toInstant(TimeZone.of("UTC+9")),
            endsAt = LocalDateTime.parse("2021-10-20T10:50:00")
                .toInstant(TimeZone.of("UTC+9")),
            speakers = listOf(
                Speaker("taka", "https://github.com/takahirom.png"),
                Speaker("ry", "https://github.com/ry-itto.png")
            )
        ),
        TimetableItem.Special(
            MultiLangText("Closing", "Closing"),
            startsAt = LocalDateTime.parse("2021-10-21T18:00:00")
                .toInstant(TimeZone.of("UTC+9")),
            endsAt = LocalDateTime.parse("2021-10-21T18:20:00")
                .toInstant(TimeZone.of("UTC+9")),
        ),
    )
)
