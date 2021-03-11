package io.github.droidkaigi.feeder

import io.github.droidkaigi.feeder.generated._fakeFeedContents
import kotlin.reflect.KClass

data class PodcastPlayingState(
    val id: String,
    val playingType: Type
) {
    enum class Type {
        PLAY,
        PAUSE,
        STOP
    }
}

data class FeedContents(
    val feedItemContents: List<FeedItem> = listOf(),
    val favorites: Set<String> = setOf(),
    val podcastPlayingState: PodcastPlayingState? = null,
) {

    data class Content(
        val feedItem: FeedItem,
        val favorited: Boolean,
        val podcastPlayingType: PodcastPlayingState.Type? = null,
    )

    val contents by lazy {
        feedItemContents.map { feedItem ->
            when (feedItem) {
                is FeedItem.Podcast -> Content(
                    feedItem = feedItem,
                    favorited = favorites.contains(feedItem.id),
                    podcastPlayingType = if (feedItem.id == podcastPlayingState?.id) {
                        podcastPlayingState.playingType
                    } else {
                        PodcastPlayingState.Type.STOP
                    }
                )
                is FeedItem.Video, is FeedItem.Blog ->
                    Content(feedItem, favorites.contains(feedItem.id))
            }
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
