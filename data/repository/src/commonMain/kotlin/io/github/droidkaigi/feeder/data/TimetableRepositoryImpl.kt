package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.repository.TimetableRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class TimetableRepositoryImpl(
    private val droidKaigi2021Api: DroidKaigi2021Api,
//    private val timetableItemDao: TimetableItemDao,
    private val dataStore: UserDataStore,
) : TimetableRepository {
    override fun timetableContents(): Flow<TimetableContents> {
        return flow { emit(droidKaigi2021Api.fetch()) }
//        return dataStore.favorites()
//            .combine(timetableItemDao.selectAll()) { favorites, dbtimetable ->
//                timetableContents(dbtimetable.sortedByDescending { it.publishedAt }, favorites)
//            }
    }

    override suspend fun refresh() {
//        val newtimetables = droidKaigi2021Api.fetch()
//        timetableItemDao.deleteAll()
//        timetableItemDao.insert(newtimetables)
    }

    override suspend fun addFavorite(id: String) {
        dataStore.addFavoriteTimetable(id)
    }

    override suspend fun removeFavorite(id: String) {
        dataStore.removeFavoriteTimetable(id)
    }
}
