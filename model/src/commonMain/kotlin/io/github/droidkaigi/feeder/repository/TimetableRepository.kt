package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.TimetableContents
import kotlinx.coroutines.flow.Flow

interface TimetableRepository {
    fun timetableContents(): Flow<TimetableContents>

    suspend fun refresh()
}
