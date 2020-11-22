package io.github.droidkaigi.confsched2021.news

import com.soywiz.klock.DateTimeTz

sealed class News {
    abstract val id: String
    abstract val date: DateTimeTz
    abstract val collection: String
    abstract val image: Image
    abstract val media: String
    abstract val localedContents: LocaledContents

    data class Blog(
        override val id: String,
        override val date: DateTimeTz,
        override val collection: String,
        override val image: Image,
        override val media: String,
        override val localedContents: LocaledContents
    ) : News()

    data class Video(
        override val id: String,
        override val date: DateTimeTz,
        override val collection: String,
        override val image: Image,
        override val media: String,
        override val localedContents: LocaledContents
    ) : News()

    data class Podcast(
        override val id: String,
        override val date: DateTimeTz,
        override val collection: String,
        override val image: Image,
        override val media: String,
        override val localedContents: LocaledContents
    ) : News()

    data class Other(
        override val id: String,
        override val date: DateTimeTz,
        override val collection: String,
        override val image: Image,
        override val media: String,
        override val localedContents: LocaledContents
    ) : News()
}
