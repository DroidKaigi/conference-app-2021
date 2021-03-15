package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerKtorContributorApi @Inject constructor(
    networkService: NetworkService,
) : KtorContributorApi(networkService)
