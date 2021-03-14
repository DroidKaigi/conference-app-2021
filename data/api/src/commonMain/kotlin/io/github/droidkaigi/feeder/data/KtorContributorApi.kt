package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.data.response.ContributorsResponse

open class KtorContributorApi(
    private val networkService: NetworkService,
) : ContributorApi {
    override suspend fun fetch(): List<Contributor> = networkService.get<ContributorsResponse>(
        "https://ssot-api-staging.an.r.appspot.com/contributors",
        needAuth = true
    ).toContributorList()
}

fun ContributorsResponse.toContributorList(): List<Contributor> {
    return contributors.map {
        Contributor(
            id = it.id,
            name = it.githubUsername,
            url = "https://github.com/${it.githubUsername}",
            image = it.iconUrl
        )
    }
}
