package io.github.droidkaigi.confsched2021.news.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerArticleRepository @Inject constructor(
    api: Api,
    dataDataStore: UserDataStore
) : ArticleRepository(
    api,
    dataDataStore
)