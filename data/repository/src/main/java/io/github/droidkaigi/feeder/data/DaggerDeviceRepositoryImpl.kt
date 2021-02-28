package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerDeviceRepositoryImpl @Inject constructor(
    deviceApi: DeviceApi,
    userDataStore: UserDataStore,
) : DeviceRepositoryImpl(deviceApi, userDataStore)
