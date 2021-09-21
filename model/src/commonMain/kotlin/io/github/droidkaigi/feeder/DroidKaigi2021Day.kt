package io.github.droidkaigi.feeder

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

enum class DroidKaigi2021Day(val start: Instant, val end: Instant) {
    Day1(
        start = LocalDateTime
            .parse("2021-10-19T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2021-10-20T00:00:00")
            .toInstant(TimeZone.of("UTC+9"))
    ),
    Day2(
        start = LocalDateTime
            .parse("2021-10-20T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2021-10-21T00:00:00")
            .toInstant(TimeZone.of("UTC+9"))
    ),
    Day3(
        start = LocalDateTime
            .parse("2021-10-21T00:00:00")
            .toInstant(TimeZone.of("UTC+9")),
        end = LocalDateTime
            .parse("2021-10-22T00:00:00")
            .toInstant(TimeZone.of("UTC+9"))
    );
}
