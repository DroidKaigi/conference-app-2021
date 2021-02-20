package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Author
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Image
import io.github.droidkaigi.feeder.Media
import io.github.droidkaigi.feeder.data.response.FeedsResponse
import io.github.droidkaigi.feeder.data.response.Thumbnail
import io.ktor.client.HttpClient
import io.ktor.client.request.get

open class KtorFeedApi(
    private val authApi: AuthApi,
    private val httpClient: HttpClient,
) : FeedApi {

    override suspend fun fetch(): List<FeedItem> = authApi.authenticated {
        val feedsResponse = httpClient.get<FeedsResponse>(
            "https://ssot-api-staging.an.r.appspot.com/feeds/recent",
        )
        feedsResponse.toFeedList()
    }
}

fun FeedsResponse.toFeedList() =
    articles.map { article ->
        FeedItem.Blog(
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
            FeedItem.Podcast(
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
            FeedItem.Video(
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
