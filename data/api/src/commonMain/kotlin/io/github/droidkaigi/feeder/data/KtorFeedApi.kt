package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Author
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.FeedItemId
import io.github.droidkaigi.feeder.Image
import io.github.droidkaigi.feeder.Media
import io.github.droidkaigi.feeder.MultiLangText
import io.github.droidkaigi.feeder.data.response.FeedsResponse
import io.github.droidkaigi.feeder.data.response.Speaker
import io.github.droidkaigi.feeder.data.response.Thumbnail
import io.ktor.http.Url
import io.ktor.http.authority
import io.ktor.http.fullPath

open class KtorFeedApi(
    private val networkService: NetworkService,
) : FeedApi {

    override suspend fun fetch(): List<FeedItem> = networkService.get<FeedsResponse>(
        "https://ssot-api-staging.an.r.appspot.com/feeds/recent",
        needAuth = true
    ).toFeedList()
}

fun FeedsResponse.toFeedList() =
    articles.map { article ->
        FeedItem.Blog(
            id = FeedItemId(article.id),
            publishedAt = article.publishedAt,
            image = article.thumbnail.toImage(),
            media = Media.Medium,
            title = MultiLangText(
                jaTitle = article.title,
                enTitle = article.title,
            ),
            summary = MultiLangText(
                jaTitle = article.summary,
                enTitle = article.summary,
            ),
            link = article.link,
            language = article.language,
            author = Author(
                name = article.authorName,
                link = article.authorUrl
            )
        )
    } +
        recordings.map { recording ->
            FeedItem.Video(
                id = FeedItemId(recording.id),
                publishedAt = recording.publishedAt,
                image = recording.thumbnail.toImage(),
                media = Media.YouTube,
                title = MultiLangText(
                    jaTitle = recording.multiLangTitle.japanese,
                    enTitle = recording.multiLangTitle.english,
                ),
                summary = MultiLangText(
                    jaTitle = recording.multiLangSummary.japanese,
                    enTitle = recording.multiLangSummary.english,
                ),
                link = recording.link,
            )
        } +
        episodes.map { recording ->
            FeedItem.Podcast(
                id = FeedItemId(recording.id),
                publishedAt = recording.publishedAt,
                image = recording.thumbnail.toImage(),
                media = Media.DroidKaigiFM,
                title = MultiLangText(
                    jaTitle = recording.title,
                    enTitle = recording.title,
                ),
                summary = MultiLangText(
                    jaTitle = recording.summary,
                    enTitle = recording.summary,
                ),
                link = recording.link,
                speakers = recording.speakers.map { it.toSpeaker() },
                podcastLink = with(Url(recording.link)) {
                    val number = fullPath.split("/").last()
                    "${protocol.name}://$authority/fm/audio/droidkaigi-fm_$number.mp3"
                }
            )
        }

private fun Thumbnail.toImage(): Image {
    return Image(
        smallUrl,
        standardUrl,
        largeUrl
    )
}

private fun Speaker.toSpeaker(): io.github.droidkaigi.feeder.Speaker {
    return io.github.droidkaigi.feeder.Speaker(
        name,
        iconUrl
    )
}
