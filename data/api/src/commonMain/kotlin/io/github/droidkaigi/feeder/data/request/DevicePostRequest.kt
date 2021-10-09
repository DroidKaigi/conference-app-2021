package io.github.droidkaigi.feeder.data.request

import kotlinx.serialization.Serializable

@Serializable
data class DevicePostRequest(
    val osName: String,
)

expect fun platform(): String
