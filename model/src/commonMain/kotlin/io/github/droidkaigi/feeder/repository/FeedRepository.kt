package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedItem
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun feedContents(forceUpdate: Boolean = false): Flow<FeedContents>

    suspend fun addFavorite(feedItem: FeedItem)

    suspend fun removeFavorite(feedItem: FeedItem)
}
