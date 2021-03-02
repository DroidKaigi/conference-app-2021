package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.DeviceInfo

interface DeviceApi {
    suspend fun create(): DeviceInfo
    suspend fun update(deviceId: String, deviceToken: String?): DeviceInfo
}
