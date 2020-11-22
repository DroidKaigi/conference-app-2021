package io.github.droidkaigi.confsched2021.news.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerArticleRepository @Inject constructor(
    newsApi: NewsApi,
    dataDataStore: UserDataStore
) : ArticleRepository(
    newsApi,
    dataDataStore
)