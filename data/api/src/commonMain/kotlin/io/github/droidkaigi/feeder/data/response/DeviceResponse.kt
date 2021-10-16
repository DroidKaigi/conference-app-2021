package io.github.droidkaigi.feeder.data.response

import kotlinx.serialization.Serializable

@Serializable
data class DeviceResponse(
    val device: Device,
)

@Serializable
data class Device(
    val id: String,
    val isPushSupported: Boolean,
)
