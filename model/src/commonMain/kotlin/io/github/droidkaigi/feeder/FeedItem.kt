package io.github.droidkaigi.feeder

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

sealed class FeedItem {
    abstract val id: FeedItemId
    abstract val publishedAt: Instant
    abstract val image: Image
    abstract val media: Media
    abstract val title: MultiLangText
    abstract val summary: MultiLangText
    abstract val link: String

    data class Blog(
        override val id: FeedItemId,
        override val publishedAt: Instant,
        override val image: Image,
        override val media: Media,
        override val title: MultiLangText,
        override val summary: MultiLangText,
        override val link: String,
        val language: String,
        val author: Author,
    ) : FeedItem()

    data class Video(
        override val id: FeedItemId,
        override val publishedAt: Instant,
        override val image: Image,
        override val media: Media,
        override val title: MultiLangText,
        override val summary: MultiLangText,
        override val link: String,
    ) : FeedItem()

    data class Podcast(
        override val id: FeedItemId,
        override val publishedAt: Instant,
        override val image: Image,
        override val media: Media,
        override val title: MultiLangText,
        override val summary: MultiLangText,
        override val link: String,
        val speakers: List<Speaker>,
        val podcastLink: String,
    ) : FeedItem()

    fun publishedDateString(): String {
        val localDate = publishedAt
            .toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDate.year}/${localDate.monthNumber}/${localDate.dayOfMonth}"
    }
}
