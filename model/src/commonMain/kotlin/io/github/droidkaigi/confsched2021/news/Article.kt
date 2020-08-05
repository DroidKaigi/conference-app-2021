package io.github.droidkaigi.confsched2021.news

import com.soywiz.klock.Date

class Article(
    val id: String,
    val date: Date,
    val collection: String,
    val image: Image,
    val media: String,
    val localedContents: LocaledContents
)