package io.github.droidkaigi.feeder.data.session.response


import kotlinx.serialization.Serializable

@Serializable
internal data class CompanyNameResponse(
    val ja: String,
    val en: String
)
