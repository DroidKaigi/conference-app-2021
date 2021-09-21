package io.github.droidkaigi.feeder.data.session.response

import kotlinx.serialization.Serializable

@Serializable
internal data class SessionAllResponse(
    val sessions: List<SessionResponse>,
    val rooms: List<RoomResponse>?,
    val speakers: List<SpeakerResponse>?,
    val categories: List<CategoryResponse>?
)
