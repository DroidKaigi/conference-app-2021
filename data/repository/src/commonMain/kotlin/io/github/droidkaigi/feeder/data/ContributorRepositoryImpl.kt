package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.repository.ContributorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

open class ContributorRepositoryImpl(
    private val contributorApi: ContributorApi,
) : ContributorRepository {
    override fun contributorContents(): Flow<List<Contributor>> {
        return flow {
            emit(contributorApi.fetch())
        }
    }
}
