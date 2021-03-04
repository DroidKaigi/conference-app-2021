package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

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
