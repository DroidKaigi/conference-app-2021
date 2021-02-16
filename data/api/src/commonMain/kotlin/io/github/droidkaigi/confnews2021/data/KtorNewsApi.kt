package io.github.droidkaigi.confnews2021.data

import io.github.droidkaigi.confnews2021.Author
import io.github.droidkaigi.confnews2021.Image
import io.github.droidkaigi.confnews2021.Media
import io.github.droidkaigi.confnews2021.News
import io.github.droidkaigi.confnews2021.data.response.FeedsResponse
import io.github.droidkaigi.confnews2021.data.response.Thumbnail
import io.ktor.client.HttpClient
import io.ktor.client.request.get

open class KtorNewsApi(
    private val authApi: AuthApi,
    private val httpClient: HttpClient,
) : NewsApi {

    override suspend fun fetch(): List<News> = authApi.authenticated {
        val feedsResponse = httpClient.get<FeedsResponse>(
            "https://ssot-api-staging.an.r.appspot.com/feeds/recent",
        )
        feedsResponse.toNewsList()
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
