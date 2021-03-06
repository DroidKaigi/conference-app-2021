package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.data.response.ContributorsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class KtorContributorApi(
    private val authApi: AuthApi,
    private val httpClient: HttpClient,
) : ContributorApi {
    override suspend fun fetch(): List<Contributor> = authApi.authenticated {
        httpClient.get<ContributorsResponse>("https://ssot-api-staging.an.r.appspot.com/contributors") {
            contentType(ContentType.Application.Json)
        }.toContributorList()
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
