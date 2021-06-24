package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.FeedContents
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun feedContents(): Flow<FeedContents>

    suspend fun refresh()

    suspend fun addFavorite(id: String)

    suspend fun removeFavorite(id: String)
}
