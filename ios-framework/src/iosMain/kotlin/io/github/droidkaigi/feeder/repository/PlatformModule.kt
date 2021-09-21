package io.github.droidkaigi.feeder.repository

import org.koin.dsl.module

val platformModule = module {
    single<IosContributorRepository> {
        IosContributorRepositoryImpl(get())
    }

    single<IosDeviceRepository> {
        IosDeviceRepositoryImpl(get())
    }

    single<IosFeedRepository> {
        IosFeedRepositoryImpl(get())
    }

    single<IosStaffRepository> {
        IosStaffRepositoryImpl(get())
    }

    single<IosTimetableRepository> {
        IosTimetableRepositoryImpl(get())
    }

    single<IosThemeRepository> {
        IosThemeRepositoryImpl(get())
    }
}
