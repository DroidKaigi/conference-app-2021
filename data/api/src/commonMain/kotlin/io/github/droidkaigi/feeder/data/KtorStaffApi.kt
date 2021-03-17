package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.data.response.StaffResponse

open class KtorStaffApi(
    private val networkService: NetworkService,
) : StaffApi {

    override suspend fun fetch(): List<Staff> = networkService.get<StaffResponse>(
        "https://ssot-api-staging.an.r.appspot.com/staff",
        needAuth = true
    ).toStaffList()
}

fun StaffResponse.toStaffList() =
    staff.map {
        Staff(it.id, it.name, it.githubUrl ?: "", it.iconUrl)
    }
