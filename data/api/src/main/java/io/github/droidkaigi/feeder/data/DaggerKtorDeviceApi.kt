package io.github.droidkaigi.feeder.data

import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerKtorDeviceApi @Inject constructor(
    authApi: AuthApi,
    networkService: NetworkService,
) : KtorDeviceApi(authApi, networkService)
