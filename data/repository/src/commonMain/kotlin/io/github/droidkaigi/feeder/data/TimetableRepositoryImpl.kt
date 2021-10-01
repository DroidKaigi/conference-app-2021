package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.TimetableItemId
import io.github.droidkaigi.feeder.TimetableItemList
import io.github.droidkaigi.feeder.repository.TimetableRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

open class TimetableRepositoryImpl(
    private val droidKaigi2021Api: DroidKaigi2021Api,
    private val timetableItemDao: TimetableItemDao,
    private val dataStore: UserDataStore,
) : TimetableRepository {
    override fun timetableContents(): Flow<TimetableContents> {
        return dataStore.favoriteTimetableItemIds()
            .combine(timetableItemDao.selectAll()) { favorites, dbtimetable ->
                TimetableContents(
                    timetableItems = TimetableItemList(
                        timetableItems = dbtimetable.sortedBy { it.startsAt },
                    ),
                    favorites = favorites.map { TimetableItemId(it.value) }.toSet(),
                )
            }
    }

    override suspend fun refresh() {
        val newtimetables = droidKaigi2021Api.fetch().timetableItems
        timetableItemDao.replace(newtimetables)
    }

    override suspend fun addFavorite(id: TimetableItemId) {
        dataStore.addFavoriteTimetableItemId(id)
    }

    override suspend fun removeFavorite(id: TimetableItemId) {
        dataStore.removeFavoriteTimetableItemId(id)
    }
}
