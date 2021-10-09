package io.github.droidkaigi.feeder.data.session.response

import kotlinx.serialization.Serializable

@Serializable
internal data class RoomResponse(
    val name: LocaledResponse,
    val id: Int,
    val sort: Int,
)
