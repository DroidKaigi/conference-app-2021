package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerKtorStaffApi @Inject constructor(
    networkService: NetworkService,
) : KtorStaffApi(networkService)
