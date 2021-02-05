package io.github.droidkaigi.confsched2021.news.data

import io.github.droidkaigi.confsched2021.news.Author
import io.github.droidkaigi.confsched2021.news.Image
import io.github.droidkaigi.confsched2021.news.Media
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.data.response.FeedsResponse
import io.github.droidkaigi.confsched2021.news.data.response.InstantSerializer
import io.github.droidkaigi.confsched2021.news.data.response.Thumbnail
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

open class KtorNewsApi(private val firebaseAuthApi: FirebaseAuthApi) :
    NewsApi {
    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json = kotlinx.serialization.json.Json {
                    serializersModule = SerializersModule {
                        contextual(InstantSerializer)
                    }
                }
            )
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.ALL
        }
    }

    override suspend fun fetch(): List<News> {
        val feedsResponse = httpClient.get<FeedsResponse>(
            "https://ssot-api-staging.an.r.appspot.com/feeds/recent",
        ) {
            try {
                val user = firebaseAuthApi.user()
                val idToken = user.getIdToken(false)
                headers {
                    idToken?.let {
                        set("Authorization", "Bearer $idToken")
                    }
                }
            } catch (illegalStateException: IllegalStateException) {
                illegalStateException.printStackTrace()
                // fail to initialize firebase
                // currently ignore it
            }
        }
        return feedsResponse.toNewsList()
    }
}

fun FeedsResponse.toNewsList() =
    articles.map { article ->
        News.Blog(
            id = article.id,
            publishedAt = article.publishedAt,
            image = article.thumbnail.toImage(),
            media = Media.Medium,
            title = article.title,
            summary = article.summary,
            link = article.link,
            language = article.language,
            author = Author(
                name = article.authorName,
                link = article.authorUrl
            )
        )
    } +
        recordings.map { recording ->
            News.Podcast(
                id = recording.id,
                publishedAt = recording.publishedAt,
                image = recording.thumbnail.toImage(),
                media = Media.YouTube,
                // TODO: Use MultiLangText
                title = recording.multiLangTitle.japanese,
                // TODO: Use MultiLangText
                summary = recording.multiLangSummary.japanese,
                link = recording.link,
            )
        } +
        episodes.map { recording ->
            News.Video(
                id = recording.id,
                publishedAt = recording.publishedAt,
                image = recording.thumbnail.toImage(),
                media = Media.DroidKaigiFM,
                title = recording.title,
                summary = recording.title,
                link = recording.link,
            )
        }

private fun Thumbnail.toImage(): Image {
    return Image(
        smallUrl,
        standardUrl,
        largeUrl
    )
}
