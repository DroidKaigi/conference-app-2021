package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.data.response.StaffResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get

open class KtorStaffApi(
    private val authApi: AuthApi,
    private val networkService: NetworkService,
) : StaffApi {

    override suspend fun fetch(): List<Staff> = authApi.authenticated {
        val staffResponse = networkService.httpClient.get<StaffResponse>(
            "https://ssot-api-staging.an.r.appspot.com/staff",
        )
        staffResponse.toStaffList()
    }
}

fun StaffResponse.toStaffList() =
    staff.map {
        Staff(it.id, it.name, it.githubUrl ?: "", it.iconUrl)
    }
