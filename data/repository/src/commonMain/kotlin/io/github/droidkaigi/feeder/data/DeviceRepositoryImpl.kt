package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.repository.DeviceRepository
import kotlinx.coroutines.flow.first

open class DeviceRepositoryImpl(
    private val deviceApi: DeviceApi,
    private val userDataStore: UserDataStore,
) : DeviceRepository {
    override suspend fun updateDeviceToken(deviceToken: String?) {
        val deviceId = userDataStore.deviceId().first() ?: deviceApi.create().id.also {
            userDataStore.setDeviceId(it)
        }
        deviceApi.update(deviceId, deviceToken)
    }
}
