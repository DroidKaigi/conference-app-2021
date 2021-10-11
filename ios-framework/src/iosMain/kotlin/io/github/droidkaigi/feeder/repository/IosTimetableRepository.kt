package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.NonNullFlowWrapper
import io.github.droidkaigi.feeder.NonNullSuspendWrapper
import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.TimetableItemId

interface IosTimetableRepository {
    fun timetableContents(): NonNullFlowWrapper<TimetableContents>
    fun refresh(): NonNullSuspendWrapper<Unit>
    fun addFavorite(id: TimetableItemId): NonNullSuspendWrapper<Unit>
    fun removeFavorite(id: TimetableItemId): NonNullSuspendWrapper<Unit>
}

class IosTimetableRepositoryImpl(
    private val timetableRepository: TimetableRepository,
) : IosTimetableRepository {
    override fun timetableContents(): NonNullFlowWrapper<TimetableContents> {
        return NonNullFlowWrapper(
            timetableRepository.timetableContents()
        )
    }

    override fun refresh(): NonNullSuspendWrapper<Unit> {
        return NonNullSuspendWrapper {
            timetableRepository.refresh()
        }
    }

    override fun addFavorite(id: TimetableItemId): NonNullSuspendWrapper<Unit> {
        return NonNullSuspendWrapper {
            timetableRepository.addFavorite(id)
        }
    }

    override fun removeFavorite(id: TimetableItemId): NonNullSuspendWrapper<Unit> {
        return NonNullSuspendWrapper {
            timetableRepository.removeFavorite(id)
        }
    }
}
