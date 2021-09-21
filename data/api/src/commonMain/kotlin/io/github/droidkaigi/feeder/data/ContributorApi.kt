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
                        "id" : 1,
                        "username" : "takahirom",
                        "iconUrl" : "https://avatars2.githubusercontent.com/u/1386930?v=4"
                    },
                    {
                        "id" : 2,
                        "username" : "mhidaka",
                        "iconUrl" : "https://avatars2.githubusercontent.com/u/322019?v=4"
                    }
                ]
            }
        """.trimIndent()
        Json {}.decodeFromString<ContributorsResponse>(responseText).toContributorList()
    }
}
