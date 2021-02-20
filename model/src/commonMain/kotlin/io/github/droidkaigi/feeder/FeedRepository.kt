package io.github.droidkaigi.feeder

import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun feedContents(): Flow<FeedContents>

    suspend fun addFavorite(feedItem: FeedItem)

    suspend fun removeFavorite(feedItem: FeedItem)
}
