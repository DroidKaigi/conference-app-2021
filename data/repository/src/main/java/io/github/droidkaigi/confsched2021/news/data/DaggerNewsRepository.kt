package io.github.droidkaigi.confsched2021.news.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerNewsRepository @Inject constructor(
    newsApi: NewsApi,
    dataDataStore: UserDataStore,
) : NewsRepository(
    newsApi,
    dataDataStore,
)
