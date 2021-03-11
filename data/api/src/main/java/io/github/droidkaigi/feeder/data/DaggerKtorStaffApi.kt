package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerKtorStaffApi @Inject constructor(
    authApi: AuthApi,
    networkService: NetworkService,
) : KtorStaffApi(authApi, networkService)
