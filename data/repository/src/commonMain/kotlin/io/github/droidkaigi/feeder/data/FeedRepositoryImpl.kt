package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedItemId
import io.github.droidkaigi.feeder.repository.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

open class FeedRepositoryImpl(
    private val feedApi: FeedApi,
    private val feedItemDao: FeedItemDao,
    private val dataStore: UserDataStore,
) : FeedRepository {
    override fun feedContents(): Flow<FeedContents> {
        return dataStore.favorites()
            .combine(feedItemDao.selectAll()) { favorites, dbFeed ->
                FeedContents(dbFeed.sortedByDescending { it.publishedAt }, favorites)
            }
    }

    override suspend fun refresh() {
        val newFeeds = feedApi.fetch()
        feedItemDao.deleteAll()
        feedItemDao.insert(newFeeds)
    }

    override suspend fun addFavorite(id: FeedItemId) {
        dataStore.addFavorite(id)
    }

    override suspend fun removeFavorite(id: FeedItemId) {
        dataStore.removeFavorite(id)
    }
}
