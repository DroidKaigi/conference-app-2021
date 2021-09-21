package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.repository.ContributorRepository
import io.github.droidkaigi.feeder.repository.DeviceRepository
import io.github.droidkaigi.feeder.repository.FeedRepository
import io.github.droidkaigi.feeder.repository.StaffRepository
import io.github.droidkaigi.feeder.repository.ThemeRepository
import io.github.droidkaigi.feeder.repository.TimetableRepository
import io.github.droidkaigi.timetableer.data.TimetableRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<FeedRepository> {
        FeedRepositoryImpl(get(), get(), get())
    }

    single<StaffRepository> {
        StaffRepositoryImpl(get())
    }

    single<DeviceRepository> {
        DeviceRepositoryImpl(get(), get())
    }

    single<ContributorRepository> {
        ContributorRepositoryImpl(get())
    }

    single<ThemeRepository> {
        ThemeRepositoryImpl(get())
    }

    single<TimetableRepository> {
        TimetableRepositoryImpl(get(), get())
    }
}
