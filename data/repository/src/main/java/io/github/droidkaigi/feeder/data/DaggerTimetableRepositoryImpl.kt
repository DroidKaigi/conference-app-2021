package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.timetableer.data.TimetableRepositoryImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerTimetableRepositoryImpl @Inject constructor(
    droidKaigi2021Api: DroidKaigi2021Api,
    dataDataStore: UserDataStore,
) : TimetableRepositoryImpl(
    droidKaigi2021Api,
    dataDataStore,
)
