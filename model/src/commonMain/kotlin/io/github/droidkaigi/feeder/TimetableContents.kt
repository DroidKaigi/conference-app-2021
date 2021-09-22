package io.github.droidkaigi.feeder

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

data class TimetableContents(
    val timetableItems: TimetableItemList = TimetableItemList(),
    val favorites: Set<String> = setOf(),
)

fun TimetableContents?.orEmptyContents(): TimetableContents = this ?: TimetableContents()

fun fakeTimetableContents(): TimetableContents = TimetableContents(
    timetableItems = TimetableItemList(
        listOf(
            TimetableItem.Special(
                id = "1",
                title = MultiLangText("ウェルカムトーク", "Welcome Talk"),
                startsAt = LocalDateTime.parse("2021-10-20T10:00:00")
                    .toInstant(TimeZone.of("UTC+9")),
                endsAt = LocalDateTime.parse("2021-10-20T10:20:00")
                    .toInstant(TimeZone.of("UTC+9")),
            ),
            TimetableItem.Session(
                id = "2",
                title = MultiLangText("DroidKaigiのアプリのアーキテクチャ", "DroidKaigi App Architecture"),
                startsAt = LocalDateTime.parse("2021-10-20T10:30:00")
                    .toInstant(TimeZone.of("UTC+9")),
                endsAt = LocalDateTime.parse("2021-10-20T10:50:00")
                    .toInstant(TimeZone.of("UTC+9")),
                speakers = listOf(
                    TimetableSpeaker("taka", "https://github.com/takahirom.png"),
                    TimetableSpeaker("ry", "https://github.com/ry-itto.png")
                ),
            ),
            TimetableItem.Special(
                id = "3",
                title = MultiLangText("Closing", "Closing"),
                startsAt = LocalDateTime.parse("2021-10-21T18:00:00")
                    .toInstant(TimeZone.of("UTC+9")),
                endsAt = LocalDateTime.parse("2021-10-21T18:20:00")
                    .toInstant(TimeZone.of("UTC+9")),
            ),
        )
    )
)
