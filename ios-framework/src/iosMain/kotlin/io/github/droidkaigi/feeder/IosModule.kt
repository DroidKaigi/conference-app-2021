package io.github.droidkaigi.feeder

import io.github.droidkaigi.feeder.data.apiModule
import io.github.droidkaigi.feeder.data.databaseModule
import io.github.droidkaigi.feeder.data.repositoryModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(authenticator: Authenticator): KoinApplication {
    return startKoin {
        modules(
            databaseModule,
            apiModule,
            repositoryModule,
            module {
                single { authenticator }
            }
        )
    }
}
