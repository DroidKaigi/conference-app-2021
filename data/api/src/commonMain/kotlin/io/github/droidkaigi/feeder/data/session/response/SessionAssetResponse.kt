package io.github.droidkaigi.feeder.data.session.response

import kotlinx.serialization.Serializable

@Serializable
internal data class SessionAssetResponse(
    val videoUrl: String?,
    val slideUrl: String?,
)
