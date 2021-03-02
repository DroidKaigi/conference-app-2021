package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerSQLDelightFeedItemDao @Inject constructor(database: Database) : FeedItemDao(database)
