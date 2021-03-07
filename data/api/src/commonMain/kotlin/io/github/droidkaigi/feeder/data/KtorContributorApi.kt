package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.data.response.ContributorResponse
import io.github.droidkaigi.feeder.data.response.ContributorsResponse
import io.ktor.client.HttpClient

open class KtorContributorApi(
    private val authApi: AuthApi,
    private val httpClient: HttpClient,
) : ContributorApi {
    override suspend fun fetch(): List<Contributor> = authApi.authenticated {
        // TODO: 2021/03/06 remove comment out when api implemented
        fakeContributorsResponse().toContributorList()
//        httpClient.get<ContributorsResponse>("https://ssot-api-staging.an.r.appspot.com/contributors") {
//            contentType(ContentType.Application.Json)
//        }.toContributorList()
    }
}

private fun ContributorsResponse.toContributorList(): List<Contributor> {
    return contributors.map {
        Contributor(
            id = it.id,
            name = it.githubUsername,
            url = "https://github.com/${it.githubUsername}",
            image = it.iconUrl
        )
    }
}

private fun fakeContributorsResponse(): ContributorsResponse {
    return ContributorsResponse(
        listOf(
            ContributorResponse(
                id = "8e885b7b-6eaa-0c8f-0e1c-10cfa78c98fa",
                githubUsername = "takahirom",
                githubUserId = 1386930,
                iconUrl = "https://avatars2.githubusercontent.com/u/1386930?v=4"
            ),
            ContributorResponse(
                id = "fec7560f-1e43-4e25-bce9-a64fa00e01bd",
                githubUsername = "mhidaka",
                githubUserId = 322019,
                iconUrl = "https://avatars2.githubusercontent.com/u/322019?v=4"
            )
        )
    )
}
