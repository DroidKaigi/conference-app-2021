package io.github.droidkaigi.feeder

import io.github.droidkaigi.feeder.data.apiModule
import io.github.droidkaigi.feeder.data.databaseModule
import io.github.droidkaigi.feeder.data.repositoryModule
import io.github.droidkaigi.feeder.repository.platformModule
import kotlinx.cinterop.ObjCProtocol
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(authenticator: Authenticator): KoinApplication {
    return startKoin {
        modules(
            databaseModule,
            apiModule,
            repositoryModule,
            platformModule,
            module {
                single<ScopeProvider> { MainScopeProvider() }
                single { authenticator }
            }
        )
    }
}

fun Koin.get(objCProtocol: ObjCProtocol): Any {
    val kClazz = getOriginalKotlinClass(objCProtocol)!!
    return get(kClazz)
}
