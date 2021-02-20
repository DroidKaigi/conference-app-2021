package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

open class StaffRepository(
    private val feedApi: FeedApi,
    private val dataStore: UserDataStore,
) {
    fun feedContents(): Flow<FeedContents> {
        return dataStore.favorites()
            .combine(
                flow {
                    emit(feedApi.fetch())
                }
            ) { favorites, apiNews ->
                FeedContents(apiNews.sortedBy { it.publishedAt }, favorites)
            }
    }

    suspend fun addFavorite(feedItem: FeedItem) {
        dataStore.addFavorite(feedItem.id)
    }

    suspend fun removeFavorite(feedItem: FeedItem) {
        dataStore.removeFavorite(feedItem.id)
    }
}
