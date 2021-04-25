package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.NonNullFlowWrapper
import io.github.droidkaigi.feeder.NonNullSuspendWrapper

interface IosFeedRepository {
    fun feedContents(): NonNullFlowWrapper<FeedContents>
    fun refresh(): NonNullSuspendWrapper<Unit>
    fun addFavorite(feedItem: FeedItem): NonNullSuspendWrapper<Unit>
    fun removeFavorite(feedItem: FeedItem): NonNullSuspendWrapper<Unit>
}

class IosFeedRepositoryImpl(
    private val feedRepository: FeedRepository
) : IosFeedRepository {
    override fun feedContents(): NonNullFlowWrapper<FeedContents> {
        return NonNullFlowWrapper(
            feedRepository.feedContents()
        )
    }

    override fun refresh(): NonNullSuspendWrapper<Unit> {
        return NonNullSuspendWrapper {
            feedRepository.refresh()
        }
    }

    override fun addFavorite(feedItem: FeedItem): NonNullSuspendWrapper<Unit> {
        return NonNullSuspendWrapper {
            feedRepository.addFavorite(feedItem)
        }
    }

    override fun removeFavorite(feedItem: FeedItem): NonNullSuspendWrapper<Unit> {
        return NonNullSuspendWrapper {
            feedRepository.removeFavorite(feedItem)
        }
    }
}
