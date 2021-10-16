package io.github.droidkaigi.feeder.data.session.response

import kotlinx.serialization.Serializable

@Serializable
internal data class CategoryResponse(
    val id: Int,
    val sort: Int,
    val title: LocaledResponse,
    val items: List<CategoryItemResponse> = emptyList(),
)
