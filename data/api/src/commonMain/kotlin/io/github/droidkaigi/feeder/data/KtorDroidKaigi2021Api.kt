package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.data.session.response.SessionAllResponse

open class KtorDroidKaigi2021Api(
    private val networkService: NetworkService,
) : DroidKaigi2021Api {

    override suspend fun fetch(): TimetableContents = networkService.get<SessionAllResponse>(
        "https://ssot-api-staging.an.r.appspot.com/events/droidkaigi2021/timetable",
        needAuth = true
    ).toTimetableContents()
}
