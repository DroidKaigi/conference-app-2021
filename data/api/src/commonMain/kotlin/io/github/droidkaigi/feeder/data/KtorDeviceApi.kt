package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.DeviceInfo
import io.github.droidkaigi.feeder.data.request.DevicePostRequest
import io.github.droidkaigi.feeder.data.request.DevicePutRequest
import io.github.droidkaigi.feeder.data.request.platform
import io.github.droidkaigi.feeder.data.response.DeviceResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class KtorDeviceApi(
    private val networkService: NetworkService,
) : DeviceApi {

    override suspend fun create(): DeviceInfo = networkService.post<DeviceResponse>(
        "https://ssot-api-staging.an.r.appspot" +
            ".com/devices",
        needAuth = true
    ) {
        contentType(ContentType.Application.Json)
        body = DevicePostRequest(platform())
    }.toDeviceInfo()

    override suspend fun update(deviceId: String, deviceToken: String?): DeviceInfo =
        networkService.put<DeviceResponse>(
            "https://ssot-api-staging.an.r.appspot.com/devices/$deviceId",
            needAuth = true
        ) {
            contentType(ContentType.Application.Json)
            body = DevicePutRequest(deviceToken)
        }.toDeviceInfo()
}

private fun DeviceResponse.toDeviceInfo(): DeviceInfo {
    return DeviceInfo(
        id = device.id,
        isPushSupported = device.isPushSupported
    )
}
