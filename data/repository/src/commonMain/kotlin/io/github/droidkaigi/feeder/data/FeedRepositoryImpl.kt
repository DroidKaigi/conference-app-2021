package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

open class FeedRepositoryImpl(
    private val feedApi: FeedApi,
    private val dataStore: UserDataStore,
) : FeedRepository {
    override fun feedContents(): Flow<FeedContents> {
        return dataStore.favorites()
            .combine(
                flow {
                    emit(feedApi.fetch())
                }
            ) { favorites, apiFeed ->
                FeedContents(apiFeed.sortedBy { it.publishedAt }.reversed(), favorites)
            }
    }

    override suspend fun addFavorite(feedItem: FeedItem) {
        dataStore.addFavorite(feedItem.id)
    }

    override suspend fun removeFavorite(feedItem: FeedItem) {
        dataStore.removeFavorite(feedItem.id)
    }
}
