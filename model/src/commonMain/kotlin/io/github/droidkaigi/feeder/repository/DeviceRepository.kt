package io.github.droidkaigi.feeder.repository

interface DeviceRepository {
    suspend fun updateDeviceToken(deviceToken: String?)
}
