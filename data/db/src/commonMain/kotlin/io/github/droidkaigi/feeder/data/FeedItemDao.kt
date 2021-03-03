package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Author
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Image
import io.github.droidkaigi.feeder.Media
import io.github.droidkaigi.feeder.MultiLangText
import kotlinx.datetime.Instant

open class FeedItemDao(database: Database) {
    val blogQueries: FeedItemBlogQueries = database.feedItemBlogQueries
    val podcastQueries: FeedItemPodcastQueries = database.feedItemPodcastQueries
    val videoQueries: FeedItemVideoQueries = database.feedItemVideoQueries

    companion object {
        val blogQueriesMapper = {
                id: String,
                publishedAt: Long,
                imageSmallUrl: String,
                imageStandardUrl: String,
                imageLargeUrl: String,
                media: String,
                jaTitle: String,
                enTitle: String,
                jaSummary: String,
                enSummary: String,
                link: String,
                language: String,
                authorName: String,
                authorLink: String,
            ->
            FeedItem.Blog(
                id = id,
                publishedAt = Instant.fromEpochMilliseconds(publishedAt),
                image = Image(smallUrl = imageSmallUrl, standardUrl = imageStandardUrl, largeUrl
                = imageLargeUrl),
                media = Media.parse(media),
                title = MultiLangText(jaTitle = jaTitle, enTitle = enTitle),
                summary = MultiLangText(jaTitle = jaSummary, enTitle = enSummary),
                link = link,
                language = language,
                author = Author(name = authorName, link = authorLink)
            )
        }

        val videoQueriesMapper = {
                id: String,
                publishedAt: Long,
                imageSmallUrl: String,
                imageStandardUrl: String,
                imageLargeUrl: String,
                media: String,
                jaTitle: String,
                enTitle: String,
                jaSummary: String,
                enSummary: String,
                link: String,
            ->
            FeedItem.Video(
                id = id,
                publishedAt = Instant.fromEpochMilliseconds(publishedAt),
                image = Image(smallUrl = imageSmallUrl, standardUrl = imageStandardUrl, largeUrl
                = imageLargeUrl),
                media = Media.parse(media),
                title = MultiLangText(jaTitle = jaTitle, enTitle = enTitle),
                summary = MultiLangText(jaTitle = jaSummary, enTitle = enSummary),
                link = link
            )
        }
    }
}
