package io.github.droidkaigi.feeder.data.session.response

import kotlinx.serialization.Serializable

@Serializable
internal data class SpeakerResponse(
    val profilePicture: String = "",
    val sessions: List<Int> = emptyList(),
    val tagLine: String = "",
    val isTopSpeaker: Boolean?,
    val bio: String = "",
    val fullName: String,
    val id: String,
)
