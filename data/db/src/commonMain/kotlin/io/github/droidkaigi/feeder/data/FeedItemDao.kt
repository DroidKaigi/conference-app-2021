package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Author
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Image
import io.github.droidkaigi.feeder.Media
import io.github.droidkaigi.feeder.MultiLangText
import io.github.droidkaigi.feeder.Speaker
import kotlinx.datetime.Instant

open class FeedItemDao(database: Database) {
    val blogQueries: FeedItemBlogQueries = database.feedItemBlogQueries
    val podcastQueries: FeedItemPodcastQueries = database.feedItemPodcastQueries
    val videoQueries: FeedItemVideoQueries = database.feedItemVideoQueries
    val podcastSpeakerQueries: FeedItemPodcastSpeakerQueries =
        database.feedItemPodcastSpeakerQueries

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

fun FeedItemBlogQueries.insert(blog: FeedItem.Blog) {
    this.insert(
        FeedItemBlog(
            id = blog.id,
            publishedAt = blog.publishedAt.toEpochMilliseconds(),
            imageSmallUrl = blog.image.smallUrl,
            imageStandardUrl = blog.image.standardUrl,
            imageLargeUrl = blog.image.largeUrl,
            media = blog.media.text,
            jaTitle = blog.title.jaTitle,
            enTitle = blog.title.enTitle,
            jaSummary = blog.summary.jaTitle,
            enSummary = blog.summary.enTitle,
            link = blog.link,
            language = blog.language,
            authorName = blog.author.name,
            authorLink = blog.author.link,
        )
    )
}

fun FeedItemPodcastQueries.insert(podcast: FeedItem.Podcast) {
    this.insert(
        FeedItemPodcast(
            id = podcast.id,
            publishedAt = podcast.publishedAt.toEpochMilliseconds(),
            imageSmallUrl = podcast.image.smallUrl,
            imageStandardUrl = podcast.image.standardUrl,
            imageLargeUrl = podcast.image.largeUrl,
            media = podcast.media.text,
            jaTitle = podcast.title.jaTitle,
            enTitle = podcast.title.enTitle,
            jaSummary = podcast.summary.jaTitle,
            enSummary = podcast.summary.enTitle,
            link = podcast.link,
        )
    )
}

fun FeedItemPodcastSpeakerQueries.insert(podcastId: String, speaker: Speaker) {
    this.insert(
        FeedItemPodcastSpeaker(
            feedItemPodcastId = podcastId,
            name = speaker.name,
            iconUrl = speaker.iconUrl
        )
    )
}

fun FeedItemVideoQueries.insert(podcast: FeedItem.Video) {
    this.insert(
        FeedItemVideo(
            id = podcast.id,
            publishedAt = podcast.publishedAt.toEpochMilliseconds(),
            imageSmallUrl = podcast.image.smallUrl,
            imageStandardUrl = podcast.image.standardUrl,
            imageLargeUrl = podcast.image.largeUrl,
            media = podcast.media.text,
            jaTitle = podcast.title.jaTitle,
            enTitle = podcast.title.enTitle,
            jaSummary = podcast.summary.jaTitle,
            enSummary = podcast.summary.enTitle,
            link = podcast.link,
        )
    )
}
