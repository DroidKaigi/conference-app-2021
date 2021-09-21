package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.data.response.ContributorsResponse

open class KtorContributorApi(
    private val networkService: NetworkService,
) : ContributorApi {
    override suspend fun fetch(): List<Contributor> = networkService.get<ContributorsResponse>(
        "https://ssot-api-staging.an.r.appspot.com/events/droidkaigi2021/contributors",
        needAuth = true
    ).toContributorList()
}

fun ContributorsResponse.toContributorList(): List<Contributor> {
    return contributors.map {
        Contributor(
            id = it.id,
            name = it.username,
            url = "https://github.com/${it.username}",
            image = it.iconUrl
        )
    }
}
