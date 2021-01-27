package io.github.droidkaigi.confsched2021.news

import kotlinx.datetime.Instant

sealed class News {
    abstract val id: String
    abstract val date: Instant
    abstract val image: Image
    abstract val media: Media
    abstract val title: String
    abstract val summary: String
    abstract val link: String

    data class Blog(
        override val id: String,
        override val date: Instant,
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
        override val date: Instant,
        override val image: Image,
        override val media: Media,
        override val title: String,
        override val summary: String,
        override val link: String,
    ) : News()

    data class Podcast(
        override val id: String,
        override val date: Instant,
        override val image: Image,
        override val media: Media,
        override val title: String,
        override val summary: String,
        override val link: String,
    ) : News()
}
