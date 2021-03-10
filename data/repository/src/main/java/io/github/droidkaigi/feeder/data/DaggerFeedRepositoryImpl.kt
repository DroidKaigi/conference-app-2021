package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerFeedRepositoryImpl @Inject constructor(
    feedApi: FeedApi,
    feedItemDao: FeedItemDao,
    dataDataStore: UserDataStore,
) : FeedRepositoryImpl(
    feedApi,
    feedItemDao,
    dataDataStore,
)
