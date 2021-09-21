package io.github.droidkaigi.feeder.data.session.response

import kotlinx.serialization.Serializable

@Serializable
internal data class SpeakerResponse(
    val firstName: String?,
    val lastName: String?,
    val profilePicture: String?,
    val sessions: List<Int?>?,
    val tagLine: String?,
    val isTopSpeaker: Boolean?,
    val bio: String?,
    val fullName: String?,
    val id: String?
)
