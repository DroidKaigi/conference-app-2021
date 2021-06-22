package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.NonNullFlowWrapper
import io.github.droidkaigi.feeder.NonNullSuspendWrapper

interface IosFeedRepository {
    fun feedContents(): NonNullFlowWrapper<FeedContents>
    fun refresh(): NonNullSuspendWrapper<Unit>
    fun addFavorite(id: String): NonNullSuspendWrapper<Unit>
    fun removeFavorite(id: String): NonNullSuspendWrapper<Unit>
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

    override fun addFavorite(id: String): NonNullSuspendWrapper<Unit> {
        return NonNullSuspendWrapper {
            feedRepository.addFavorite(id)
        }
    }

    override fun removeFavorite(id: String): NonNullSuspendWrapper<Unit> {
        return NonNullSuspendWrapper {
            feedRepository.removeFavorite(id)
        }
    }
}
