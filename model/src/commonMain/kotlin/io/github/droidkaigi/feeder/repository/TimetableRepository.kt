package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.TimetableItemId
import kotlinx.coroutines.flow.Flow

interface TimetableRepository {
    fun timetableContents(): Flow<TimetableContents>

    suspend fun refresh()

    suspend fun addFavorite(id: TimetableItemId)

    suspend fun removeFavorite(id: TimetableItemId)
}
