package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.Contributor
import kotlinx.coroutines.flow.Flow

interface ContributorRepository {
    fun contributorContents(): Flow<List<Contributor>>
}
