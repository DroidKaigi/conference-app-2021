package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.NonNullFlowWrapper

interface IosContributorRepository {
    fun contributorContents(): NonNullFlowWrapper<List<Contributor>>
}

class IosContributorRepositoryImpl(
    private val contributorRepository: ContributorRepository
) : IosContributorRepository {
    override fun contributorContents(): NonNullFlowWrapper<List<Contributor>> {
        return NonNullFlowWrapper(
            contributorRepository.contributorContents()
        )
    }
}
