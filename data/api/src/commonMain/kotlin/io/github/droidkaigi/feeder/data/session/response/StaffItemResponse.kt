package io.github.droidkaigi.feeder.data.session.response

import kotlinx.serialization.Serializable

@Serializable
internal data class StaffItemResponse(
    val id: String?,
    val name: String?,
    val iconUrl: String?,
    val profileUrl: String?
)
