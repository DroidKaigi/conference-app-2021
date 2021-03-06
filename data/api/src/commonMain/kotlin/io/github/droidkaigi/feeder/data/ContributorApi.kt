package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Contributor

interface ContributorApi {
    suspend fun fetch(): List<Contributor>
}
