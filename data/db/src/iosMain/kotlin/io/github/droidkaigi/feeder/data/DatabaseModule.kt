package io.github.droidkaigi.feeder.data

import org.koin.dsl.module

val databaseModule = module {

    single {
        val sqlDriver = DriverFactory().createDriver()
        Database(sqlDriver)
    }

    single<UserDataStore> {
        DataStoreUserDataStore()
    }

    single<FeedItemDao> {
        FeedItemDaoImpl(get())
    }

    single<TimetableItemDao> {
        TimetableItemDaoImpl(get())
    }
}
