package io.github.droidkaigi.confsched2021.news.data

import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.NewsContents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

open class ArticleRepository(private val newsApi: NewsApi, private val dataStore: UserDataStore) {
    fun articles(): Flow<NewsContents> {
        return dataStore.favorites()
            .combine(flow {
            emit(newsApi.fetch())
        }) { favorites, apiArticles ->
            NewsContents(apiArticles, favorites)
        }
    }

    suspend fun addFavorite(article: News) {
        dataStore.addFavorite(article.id)
    }

    suspend fun removeFavorite(article: News) {
        dataStore.removeFavorite(article.id)
    }
}