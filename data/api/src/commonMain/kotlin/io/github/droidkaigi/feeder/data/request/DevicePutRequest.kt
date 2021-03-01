package io.github.droidkaigi.feeder.data.request

import kotlinx.serialization.Serializable

@Serializable
data class DevicePutRequest(
    val devicePushToken: String?
)
