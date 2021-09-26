package io.github.droidkaigi.feeder

import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant

@OptIn(ExperimentalTime::class)
sealed class TimetableItem(
    open val id: String,
    open val title: MultiLangText,
    open val startsAt: Instant,
    open val endsAt: Instant,
    open val category: TimetableCategory,
    open val targetAudience: String,
    open val language: TimetableLanguage,
    open val asset: TimetableAsset,
    open val levels: List<String>,
) {
    data class Session(
        override val id: String,
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        override val category: TimetableCategory,
        override val targetAudience: String,
        override val language: String,
        override val asset: TimetableAsset,
        override val levels: List<String>,
        val description: String,
        val speakers: List<TimetableSpeaker>,
        val message: MultiLangText?,
    ) : TimetableItem(
        id = id,
        title = title,
        startsAt = startsAt,
        endsAt = endsAt,
        category = category,
        targetAudience = targetAudience,
        language = language,
        asset = asset,
        levels = levels,
    )

    data class Special(
        override val id: String,
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        override val category: TimetableCategory,
        override val targetAudience: String,
        override val language: String,
        override val asset: TimetableAsset,
        override val levels: List<String>,
        val speakers: List<TimetableSpeaker> = listOf(),
    ) : TimetableItem(
        id = id,
        title = title,
        startsAt = startsAt,
        endsAt = endsAt,
        category = category,
        targetAudience = targetAudience,
        language = language,
        asset = asset,
        levels = levels,
    )
}
