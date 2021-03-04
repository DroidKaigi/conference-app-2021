package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Image
import io.github.droidkaigi.feeder.Media
import io.github.droidkaigi.feeder.MultiLangText
import io.github.droidkaigi.feeder.Speaker
import io.github.droidkaigi.feeder.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant

open class FeedRepositoryImpl(
    private val feedApi: FeedApi,
    private val feedItemDao: FeedItemDao,
    private val dataStore: UserDataStore,
) : FeedRepository {
    override fun feedContents(forceUpdate: Boolean): Flow<FeedContents> {
        return dataStore.favorites()
            .combine(
                flow {
                    val cachedFeeds by lazy { feedItemDao.selectAll() }
                    if (forceUpdate || cachedFeeds.isEmpty()) {
                        val feeds = feedApi.fetch()
                        feedItemDao.deleteAll()
                        feedItemDao.insert(feeds)
                        emit(feeds)
                    } else {
                        emit(cachedFeeds)
                    }
                }
            ) { favorites, apiFeed ->
                FeedContents(apiFeed.sortedByDescending { it.publishedAt }, favorites)
            }
    }

    override suspend fun addFavorite(feedItem: FeedItem) {
        dataStore.addFavorite(feedItem.id)
    }

    override suspend fun removeFavorite(feedItem: FeedItem) {
        dataStore.removeFavorite(feedItem.id)
    }
}

private fun FeedItemDao.selectAll(): List<FeedItem> {
    val blogFeeds = blogQueries.selectAll(FeedItemDao.blogQueriesMapper).executeAsList()
    val podcastFeeds = podcastQueries.selectAll().executeAsList().toPodcastItems()
    val videoFeeds = videoQueries.selectAll(FeedItemDao.videoQueriesMapper).executeAsList()

    return blogFeeds + podcastFeeds + videoFeeds
}

private fun FeedItemDao.insert(feeds: List<FeedItem>) {
    feeds.forEach { item ->
        when (item) {
            is FeedItem.Blog -> blogQueries.insert(item)
            is FeedItem.Podcast -> {
                podcastQueries.insert(item)
                item.speakers.forEach { speaker -> podcastSpeakerQueries.insert(item.id, speaker) }
            }
            is FeedItem.Video -> videoQueries.insert(item)
        }
    }
}

private fun List<SelectAll>.toPodcastItems(): List<FeedItem.Podcast> {
    return this.foldRight(mapOf<String, FeedItem.Podcast>()) { row, acc ->
        val feedItem = if (acc.containsKey(row.id)) {
            val oldFeedItem = acc.getValue(row.id)
            oldFeedItem.copy(speakers = oldFeedItem.speakers + Speaker(name = row.speakerName,
                iconUrl = row.speakerIconUrl))
        } else {
            FeedItem.Podcast(
                id = row.id,
                publishedAt = Instant.fromEpochMilliseconds(row.publishedAt),
                image = Image(
                    smallUrl = row.imageSmallUrl,
                    standardUrl = row.imageStandardUrl,
                    largeUrl = row.imageLargeUrl
                ),
                media = Media.parse(row.media),
                title = MultiLangText(jaTitle = row.jaTitle, enTitle = row.enTitle),
                summary = MultiLangText(jaTitle = row.jaSummary, enTitle = row.enSummary),
                link = row.link,
                speakers = listOf(Speaker(name = row.speakerName, iconUrl = row.speakerIconUrl))
            )
        }
        acc + mapOf(row.id to feedItem)
    }.values.toList()
}

private fun FeedItemDao.deleteAll() {
    blogQueries.deleteAll()
    podcastSpeakerQueries.deleteAll()
    podcastQueries.deleteAll()
    videoQueries.deleteAll()
}
