package io.github.droidkaigi.confsched2021.news.data

import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.NewsContents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

open class NewsRepository(
    private val newsApi: NewsApi,
    private val dataStore: UserDataStore,
) {
    fun newsContents(): Flow<NewsContents> {
        return dataStore.favorites()
            .combine(
                flow {
                    emit(newsApi.fetch())
                }
            ) { favorites, apiNews ->
                NewsContents(apiNews, favorites)
            }
    }

    suspend fun addFavorite(news: News) {
        dataStore.addFavorite(news.id)
    }

    suspend fun removeFavorite(news: News) {
        dataStore.removeFavorite(news.id)
    }
}
