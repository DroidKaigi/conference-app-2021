package io.github.droidkaigi.confnews2021.data

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
