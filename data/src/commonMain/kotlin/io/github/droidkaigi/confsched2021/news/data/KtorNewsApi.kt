package io.github.droidkaigi.confsched2021.news.data

import com.soywiz.klock.DateFormat
import com.soywiz.klock.parse
import io.github.droidkaigi.confsched2021.news.Image
import io.github.droidkaigi.confsched2021.news.Locale
import io.github.droidkaigi.confsched2021.news.LocaledContents
import io.github.droidkaigi.confsched2021.news.News
import kotlinx.serialization.Serializable

open class KtorNewsApi : NewsApi {
    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun fetch(): List<News> {
        return fakeNewsApi().fetch()
    }

    @Serializable
    data class NewsResponse(
        val id: String,
        val date: String,
        val collection: String,
        val image: String,
        val media: String,
        val ja: LocaledContentsResponse,
        val en: LocaledContentsResponse? = null,
    )

    @Serializable
    data class LocaledContentsResponse(val title: String, val link: String)

}

@OptIn(ExperimentalStdlibApi::class)
fun KtorNewsApi.NewsResponse.toNews(): News {
    val response = this
    val contents = buildMap<Locale, LocaledContents.Contents> {
        put(
            Locale("ja"), LocaledContents.Contents(
                title = response.ja.title,
                link = response.ja.link
            )
        )
        response.en?.let {
            put(
                Locale("en"), LocaledContents.Contents(
                    title = response.en.title,
                    link = response.en.link
                )
            )
        }
    }
    return when (response.media) {
        "YOUTUBE" -> {
            News.Video(
                id = response.id,
                date = DateFormat("yyyy-MM-dd").parse(response.date),
                collection = response.collection,
                image = Image.of(response.image),
                media = response.media,
                localedContents = LocaledContents(
                    contents
                )
            )
        }
        "BLOG" -> {
            News.Blog(
                id = response.id,
                date = DateFormat("yyyy-MM-dd").parse(response.date),
                collection = response.collection,
                image = Image.of(response.image),
                media = response.media,
                localedContents = LocaledContents(
                    contents
                )
            )
        }
        "PODCAST" -> {
            News.Podcast(
                id = response.id,
                date = DateFormat("yyyy-MM-dd").parse(response.date),
                collection = response.collection,
                image = Image.of(response.image),
                media = response.media,
                localedContents = LocaledContents(
                    contents
                )
            )
        }
        else -> {
            News.Other(
                id = response.id,
                date = DateFormat("yyyy-MM-dd").parse(response.date),
                collection = response.collection,
                image = Image.of(response.image),
                media = response.media,
                localedContents = LocaledContents(
                    contents
                )
            )
        }
    }
}
