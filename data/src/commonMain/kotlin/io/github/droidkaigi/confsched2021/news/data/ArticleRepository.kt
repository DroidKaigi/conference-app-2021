package io.github.droidkaigi.confsched2021.news.data

import io.github.droidkaigi.confsched2021.news.Article
import io.github.droidkaigi.confsched2021.news.Articles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

open class ArticleRepository(private val api: Api, private val dataStore: UserDataStore) {
    fun articles(): Flow<Articles> {
        return flow {
            val apiArticles = api.fetch()
            dataStore.favorites().collect { favorites ->
                val favoriteAppliedArticles = apiArticles.map { article: Article ->
                    if (favorites.contains(article.id)) {
                        article.copy(isFavorited = true)
                    } else {
                        article
                    }
                }
                emit(Articles(favoriteAppliedArticles))
            }
        }
    }

    suspend fun addFavorite(article: Article) {
        dataStore.addFavorite(article.id)
    }

    suspend fun removeFavorite(article: Article) {
        dataStore.removeFavorite(article.id)
    }
}