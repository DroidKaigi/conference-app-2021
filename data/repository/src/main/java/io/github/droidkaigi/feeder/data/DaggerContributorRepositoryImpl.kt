package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerContributorRepositoryImpl @Inject constructor(contributorApi: ContributorApi) :
    ContributorRepositoryImpl(contributorApi)
