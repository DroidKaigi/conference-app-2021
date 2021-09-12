package io.github.droidkaigi.feeder

import kotlinx.datetime.Instant
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
sealed class TimetableItem(
    open val title: MultiLangText,
    open val startsAt: Instant,
    open val endsAt: Instant,
) {
    data class Session(
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        val speakers: List<Speaker>,
    ) : TimetableItem(title, startsAt, endsAt)

    data class Special(
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        val speakers: List<Speaker> = listOf(),
    ) : TimetableItem(title, startsAt, endsAt)
}