package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerNewsRepositoryImpl @Inject constructor(
    newsApi: NewsApi,
    dataDataStore: UserDataStore,
) : NewsRepositoryImpl(
    newsApi,
    dataDataStore,
)
