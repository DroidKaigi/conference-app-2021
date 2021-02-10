package io.github.droidkaigi.confnews2021.data

import io.github.droidkaigi.confnews2021.News
import io.github.droidkaigi.confnews2021.NewsContents
import io.github.droidkaigi.confnews2021.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

open class NewsRepositoryImpl(
    private val newsApi: NewsApi,
    private val dataStore: UserDataStore,
) : NewsRepository {
    override fun newsContents(): Flow<NewsContents> {
        return dataStore.favorites()
            .combine(
                flow {
                    emit(newsApi.fetch())
                }
            ) { favorites, apiNews ->
                NewsContents(apiNews.sortedBy { it.publishedAt }, favorites)
            }
    }

    override suspend fun addFavorite(news: News) {
        dataStore.addFavorite(news.id)
    }

    override suspend fun removeFavorite(news: News) {
        dataStore.removeFavorite(news.id)
    }
}
