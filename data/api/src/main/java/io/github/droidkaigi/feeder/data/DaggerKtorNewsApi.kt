package io.github.droidkaigi.feeder.data

import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerKtorNewsApi @Inject constructor(
    authApi: AuthApi,
    httpClient: HttpClient,
) : KtorNewsApi(authApi, httpClient)
