package io.github.droidkaigi.feeder

import io.github.droidkaigi.feeder.generated._fakeFeedContents
import kotlin.reflect.KClass

data class PlayingPodcastState(
    val id: String,
    val url: String,
    val isPlaying: Boolean = false,
)

data class FeedContents(
    val feedItemContents: List<FeedItem> = listOf(),
    val favorites: Set<String> = setOf()
) {

    val contents by lazy {
        feedItemContents.map {
            it to favorites.contains(it.id)
        }
    }

    fun filtered(filters: Filters): FeedContents {
        var feedItems = feedItemContents.toList()
        if (filters.filterFavorite) {
            feedItems = feedItems.filter { feedItem ->
                favorites.contains(feedItem.id)
            }
        }
        return copy(feedItemContents = feedItems)
    }

    fun filterFeedType(feedItemClass: KClass<out FeedItem>): FeedContents {
        return copy(feedItemContents = feedItemContents.filter { it::class == feedItemClass })
    }

    val size get() = feedItemContents.size
}

fun FeedContents?.orEmptyContents(): FeedContents = this ?: FeedContents()
fun LoadState<FeedContents>.getContents() = getValueOrNull() ?: FeedContents()

fun fakeFeedContents(): FeedContents = _fakeFeedContents()
