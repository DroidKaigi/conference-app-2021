package io.github.droidkaigi.feeder.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ContributorsResponse(
    val status: String,
    val contributors: List<ContributorResponse>
)

@Serializable
data class ContributorResponse(
    val id: String,
    val githubUsername: String,
    val githubUserId: Long,
    val iconUrl: String
)
