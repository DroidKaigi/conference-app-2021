package io.github.droidkaigi.feeder.data.response

import kotlinx.serialization.Serializable

@Serializable
data class Staff(val id: String, val name: String, val githubUrl: String?, val iconUrl: String)

@Serializable
data class StaffResponse(var status: String, val staff: List<Staff>)
