package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.DeviceInfo
import io.github.droidkaigi.feeder.data.request.DevicePostRequest
import io.github.droidkaigi.feeder.data.request.DevicePutRequest
import io.github.droidkaigi.feeder.data.request.platform
import io.github.droidkaigi.feeder.data.response.DeviceResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

open class KtorDeviceApi(
    private val authApi: AuthApi,
    private val networkService: NetworkService,
) : DeviceApi {

    override suspend fun create(): DeviceInfo =
        authApi.authenticated {
            networkService.post<DeviceResponse>(
                "https://ssot-api-staging.an.r.appspot" +
                    ".com/devices"
            ) {
                contentType(ContentType.Application.Json)
                body = DevicePostRequest(platform())
            }.toDeviceInfo()
        }

    override suspend fun update(deviceId: String, deviceToken: String?): DeviceInfo =
        authApi.authenticated {
            networkService.put<DeviceResponse>(
                "https://ssot-api-staging.an.r.appspot.com/devices/$deviceId"
            ) {
                contentType(ContentType.Application.Json)
                body = DevicePutRequest(deviceToken)
            }.toDeviceInfo()
        }
}

private fun DeviceResponse.toDeviceInfo(): DeviceInfo {
    return DeviceInfo(
        id = device.id,
        isPushSupported = device.isPushSupported
    )
}
