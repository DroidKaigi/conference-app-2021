package io.github.droidkaigi.feeder

import kotlinx.datetime.Instant
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
sealed class TimetableItem(
    open val id: String,
    open val title: MultiLangText,
    open val startsAt: Instant,
    open val endsAt: Instant,
) {
    data class Session(
        override val id: String,
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        val speakers: List<TimetableSpeaker>,
    ) : TimetableItem(id, title, startsAt, endsAt)

    data class Special(
        override val id: String,
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        val speakers: List<TimetableSpeaker> = listOf(),
    ) : TimetableItem(id, title, startsAt, endsAt)
}
