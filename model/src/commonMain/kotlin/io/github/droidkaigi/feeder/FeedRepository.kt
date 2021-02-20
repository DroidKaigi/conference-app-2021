package io.github.droidkaigi.feeder

import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun newsContents(): Flow<FeedContents>

    suspend fun addFavorite(feedItem: FeedItem)

    suspend fun removeFavorite(feedItem: FeedItem)
}
