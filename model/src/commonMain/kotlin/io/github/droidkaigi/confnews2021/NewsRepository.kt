package io.github.droidkaigi.confnews2021

import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun newsContents(): Flow<NewsContents>

    suspend fun addFavorite(news: News)

    suspend fun removeFavorite(news: News)
}
