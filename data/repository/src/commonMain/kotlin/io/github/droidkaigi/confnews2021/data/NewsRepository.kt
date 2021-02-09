package io.github.droidkaigi.confnews2021.data

import io.github.droidkaigi.confnews2021.News
import io.github.droidkaigi.confnews2021.NewsContents
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
                NewsContents(apiNews.sortedBy { it.publishedAt }, favorites)
            }
    }

    suspend fun addFavorite(news: News) {
        dataStore.addFavorite(news.id)
    }

    suspend fun removeFavorite(news: News) {
        dataStore.removeFavorite(news.id)
    }
}
