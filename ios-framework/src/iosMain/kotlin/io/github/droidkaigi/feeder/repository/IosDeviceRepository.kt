package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.NonNullSuspendWrapper

interface IosDeviceRepository {
    fun updateDeviceToken(deviceToken: String?): NonNullSuspendWrapper<Unit>
}

class IosDeviceRepositoryImpl(
    private val deviceRepository: DeviceRepository
) : IosDeviceRepository {
    override fun updateDeviceToken(deviceToken: String?): NonNullSuspendWrapper<Unit> {
        return NonNullSuspendWrapper {
            deviceRepository.updateDeviceToken(deviceToken)
        }
    }
}
