package io.github.droidkaigi.feeder

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(): KoinApplication {
    return startKoin {
        modules(databaseModule, apiModule, repositoryModule)
    }
}
