package io.github.droidkaigi.confsched2021.news.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerArticlesRepository @Inject constructor(
    api: Api,
    dataStore: UserStore
) : ArticlesRepository(
    api,
    dataStore
)