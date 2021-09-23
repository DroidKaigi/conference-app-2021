package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.TimetableContents
import kotlinx.coroutines.flow.Flow

interface TimetableRepository {
    fun timetableContents(): Flow<TimetableContents>

    suspend fun refresh()

    suspend fun addFavorite(id: String)

    suspend fun removeFavorite(id: String)
}
