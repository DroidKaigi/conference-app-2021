package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.data.response.ContributorsResponse
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

interface ContributorApi {
    suspend fun fetch(): List<Contributor>
}

fun fakeContributorApi(error: AppError? = null): ContributorApi = object : ContributorApi {
    override suspend fun fetch(): List<Contributor> {
        if (error != null) {
            throw error
        }
        return list
    }

    val list: List<Contributor> = run {
        val responseText = """
            {
                "status" : "OK",
                "contributors" : [
                    {
                        "id" : "8e885b7b-6eaa-0c8f-0e1c-10cfa78c98fa",
                        "githubUsername" : "takahirom",
                        "githubUserId" : 1386930,
                        "iconUrl" : "https://avatars2.githubusercontent.com/u/1386930?v=4"
                    },
                    {
                        "id" : "fec7560f-1e43-4e25-bce9-a64fa00e01bd",
                        "githubUsername" : "mhidaka",
                        "githubUserId" : 322019,
                        "iconUrl" : "https://avatars2.githubusercontent.com/u/322019?v=4"
                    }
                ]
            }
        """.trimIndent()
        Json {}.decodeFromString<ContributorsResponse>(responseText).toContributorList()
    }
}
