package io.github.droidkaigi.confnews2021.data

import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerKtorNewsApi @Inject constructor(
    userApi: UserApi,
    httpClient: HttpClient,
) : KtorNewsApi(userApi, httpClient)
