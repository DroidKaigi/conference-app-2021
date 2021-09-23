package io.github.droidkaigi.feeder.data.session.response

import kotlinx.serialization.Serializable

@Serializable
internal data class SessionResponse(
    val id: String,
    val isServiceSession: Boolean,
    val title: LocaledResponse?,
    val speakers: List<String>,
    val description: String?,
    val startsAt: String?,
    val endsAt: String?,
    val language: String?,
    val roomId: Int?,
    val sessionCategoryItemId: Int?,
    val sessionType: String?,
    val message: SessionMessageResponse? = null,
    val isPlenumSession: Boolean,
    val targetAudience: String,
    val interpretationTarget: Boolean,
    val asset: SessionAssetResponse,
    val levels: List<String>
)
