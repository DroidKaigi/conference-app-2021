package io.github.droidkaigi.feeder

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

data class TimetableContents(
    val timetableItems: TimetableItemList = TimetableItemList(),
    val favorites: Set<TimetableItemId> = setOf(),
) {
    val contents by lazy {
        timetableItems.map {
            it to favorites.contains(it.id)
        }
    }

    fun filtered(filters: Filters): TimetableContents {
        var timetableItems = timetableItems.toList()
        if (filters.filterFavorite) {
            timetableItems = timetableItems.filter { timetableItem ->
                favorites.contains(timetableItem.id)
            }
        }
        return copy(timetableItems = TimetableItemList(timetableItems))
    }
}

fun TimetableContents?.orEmptyContents(): TimetableContents = this ?: TimetableContents()
fun LoadState<TimetableContents>.getContents() = getValueOrNull() ?: TimetableContents()

fun fakeTimetableContents(): TimetableContents = TimetableContents(
    timetableItems = TimetableItemList(
        listOf(
            TimetableItem.Special(
                id = TimetableItemId("1"),
                title = MultiLangText("ウェルカムトーク", "Welcome Talk"),
                startsAt = LocalDateTime.parse("2021-10-20T10:00:00")
                    .toInstant(TimeZone.of("UTC+9")),
                endsAt = LocalDateTime.parse("2021-10-20T10:20:00")
                    .toInstant(TimeZone.of("UTC+9")),
                category = TimetableCategory(
                    id = 28657,
                    title = MultiLangText("その他", "Other"),
                ),
                targetAudience = "TBW",
                language = "TBD",
                asset = TimetableAsset(null, null),
                levels = listOf(
                    "BEGINNER",
                    "INTERMEDIATE",
                    "ADVANCED",
                ),
            ),
            TimetableItem.Session(
                id = TimetableItemId("2"),
                title = MultiLangText("DroidKaigiのアプリのアーキテクチャ", "DroidKaigi App Architecture"),
                startsAt = LocalDateTime.parse("2021-10-20T10:30:00")
                    .toInstant(TimeZone.of("UTC+9")),
                endsAt = LocalDateTime.parse("2021-10-20T10:50:00")
                    .toInstant(TimeZone.of("UTC+9")),
                category = TimetableCategory(
                    id = 28654,
                    title = MultiLangText(
                        "Android FrameworkとJetpack",
                        "Android Framework and Jetpack",
                    ),
                ),
                targetAudience = "For App developer アプリ開発者向け",
                language = "JAPANESE",
                asset = TimetableAsset(
                    videoUrl = "https://www.youtube.com/watch?v=hFdKCyJ-Z9A",
                    slideUrl = "https://droidkaigi.jp/2021/",
                ),
                speakers = listOf(
                    TimetableSpeaker(
                        name = "taka",
                        iconUrl = "https://github.com/takahirom.png",
                        bio = "Likes Android",
                        tagLine = "Android Engineer"
                    ),
                    TimetableSpeaker(
                        name = "ry",
                        iconUrl = "https://github.com/ry-itto.png",
                        bio = "Likes iOS",
                        tagLine = "iOS Engineer",
                    ),
                ),
                description = "これはディスクリプションです。\nこれはディスクリプションです。\nこれはディスクリプションです。\nこれはディスクリプションです。",
                message = null,
                levels = listOf(
                    "INTERMEDIATE",
                ),
            ),
            TimetableItem.Special(
                id = TimetableItemId("3"),
                title = MultiLangText("Closing", "Closing"),
                startsAt = LocalDateTime.parse("2021-10-21T18:00:00")
                    .toInstant(TimeZone.of("UTC+9")),
                endsAt = LocalDateTime.parse("2021-10-21T18:20:00")
                    .toInstant(TimeZone.of("UTC+9")),
                targetAudience = "TBW",
                category = TimetableCategory(
                    id = 28657,
                    title = MultiLangText("その他", "Other"),
                ),
                language = "TBD",
                asset = TimetableAsset(null, null),
                levels = listOf(
                    "BEGINNER",
                    "INTERMEDIATE",
                    "ADVANCED",
                ),
            ),
        )
    ),
    favorites = setOf()
)
