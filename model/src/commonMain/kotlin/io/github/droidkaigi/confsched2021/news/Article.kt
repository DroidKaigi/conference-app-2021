package io.github.droidkaigi.confsched2021.news

import com.soywiz.klock.DateTimeTz

class Article(
    val id: String,
    val isFavorited: Boolean = false,
    val date: DateTimeTz,
    val collection: String,
    val image: Image,
    val media: String,
    val localedContents: LocaledContents
)