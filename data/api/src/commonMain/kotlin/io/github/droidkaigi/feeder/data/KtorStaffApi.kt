package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.data.response.StaffResponse

open class KtorStaffApi(
    private val networkService: NetworkService,
) : StaffApi {

    override suspend fun fetch(): List<Staff> = networkService.get<StaffResponse>(
        "https://ssot-api-staging.an.r.appspot.com/events/droidkaigi2021/staff",
        needAuth = true
    ).toStaffList()
}

fun StaffResponse.toStaffList() =
    staff.map {
        Staff(it.id, it.username, it.profileUrl ?: "", it.iconUrl)
    }
