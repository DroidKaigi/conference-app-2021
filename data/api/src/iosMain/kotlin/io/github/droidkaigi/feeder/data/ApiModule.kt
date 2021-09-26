package io.github.droidkaigi.feeder.data

import io.ktor.client.engine.ios.Ios
import org.koin.dsl.module

val apiModule = module {

    single {
        ApiHttpClient.create(
            engineFactory = Ios,
            userDataStore = get()
        )
    }

    single {
        AuthApi(get(), get(), get())
    }

    single {
        NetworkService(get(), get())
    }

    single<ContributorApi> {
        KtorContributorApi(get())
    }

    single<DeviceApi> {
        KtorDeviceApi(get())
    }

    single<FeedApi> {
        KtorFeedApi(get())
    }

    single<DroidKaigi2021Api> {
        KtorDroidKaigi2021Api(get())
    }

    single<StaffApi> {
        KtorStaffApi(get())
    }
}
