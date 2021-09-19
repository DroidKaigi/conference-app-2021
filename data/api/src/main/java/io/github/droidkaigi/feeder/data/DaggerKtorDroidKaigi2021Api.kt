package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerKtorDroidKaigi2021Api @Inject constructor(
    networkService: NetworkService,
) : KtorDroidKaigi2021Api(networkService)
