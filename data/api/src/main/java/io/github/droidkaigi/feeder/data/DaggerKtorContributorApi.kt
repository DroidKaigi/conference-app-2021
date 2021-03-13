package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerKtorContributorApi @Inject constructor(
    authApi: AuthApi,
    networkService: NetworkService,
) : KtorContributorApi(authApi, networkService)
