package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerSQLDelightTimetableItemDao @Inject constructor(
    database: Database,
) : TimetableItemDao by TimetableItemDaoImpl(database)
