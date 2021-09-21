package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.NonNullFlowWrapper
import io.github.droidkaigi.feeder.TimetableContents

interface IosTimetableRepository {
    fun timetableContents(): NonNullFlowWrapper<TimetableContents>
}

class IosTimetableRepositoryImpl(
    private val timetableRepository: TimetableRepository,
) : IosTimetableRepository {
    override fun timetableContents(): NonNullFlowWrapper<TimetableContents> {
        return NonNullFlowWrapper(
            timetableRepository.timetableContents()
        )
    }
}
