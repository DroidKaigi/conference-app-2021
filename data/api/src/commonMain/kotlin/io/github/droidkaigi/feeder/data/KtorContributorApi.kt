package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.data.response.ContributorsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

open class KtorContributorApi(
    private val authApi: AuthApi,
    private val networkService: NetworkService,
) : ContributorApi {
    override suspend fun fetch(): List<Contributor> = authApi.authenticated {
        networkService.httpClient.get<ContributorsResponse>(
            "https://ssot-api-staging.an.r.appspot.com/contributors"
        ).toContributorList()
    }
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
