package io.github.droidkaigi.feeder.data

open class FeedItemDao(database: Database) {
    val blogQueries: FeedItemBlogQueries = database.feedItemBlogQueries
    val podcastQueries: FeedItemPodcastQueries = database.feedItemPodcastQueries
    val videoQueries: FeedItemVideoQueries = database.feedItemVideoQueries
}
