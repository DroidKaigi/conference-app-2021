package io.github.droidkaigi.confsched2021.news

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

sealed class News {
    abstract val id: String
    abstract val publishedAt: Instant
    abstract val image: Image
    abstract val media: Media
    abstract val title: String
    abstract val summary: String
    abstract val link: String

    data class Blog(
        override val id: String,
        override val publishedAt: Instant,
        override val image: Image,
        override val media: Media,
        override val title: String,
        override val summary: String,
        override val link: String,
        val language: String,
        val author: Author,
    ) : News()

    data class Video(
        override val id: String,
        override val publishedAt: Instant,
        override val image: Image,
        override val media: Media,
        override val title: String,
        override val summary: String,
        override val link: String,
    ) : News()

    data class Podcast(
        override val id: String,
        override val publishedAt: Instant,
        override val image: Image,
        override val media: Media,
        override val title: String,
        override val summary: String,
        override val link: String,
    ) : News()

    fun publishedDateString(): String {
        val localDate = publishedAt
            .toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDate.year}/${localDate.monthNumber}/${localDate.dayOfMonth}"
    }
}
