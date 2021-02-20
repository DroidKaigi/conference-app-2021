package io.github.droidkaigi.feeder

import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun newsContents(): Flow<NewsContents>

    suspend fun addFavorite(news: News)

    suspend fun removeFavorite(news: News)
}
