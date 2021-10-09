package io.github.droidkaigi.feeder.data.response

import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExternalId(
    val value: String,
    val serviceName: String,
)

@Serializable
data class Thumbnail(
    val smallUrl: String,
    val standardUrl: String,
    val largeUrl: String,
)

@Serializable
data class Article(
    val id: String,
    val externalId: ExternalId,
    val title: String,
    val summary: String,
    val authorName: String,
    val authorUrl: String,
    val thumbnail: Thumbnail,
    val link: String,
    @Contextual
    val publishedAt: Instant,
    val status: String,
    val language: String,
)

@Serializable
data class Speaker(
    val name: String,
    val iconUrl: String,
)

@Serializable
data class Episode(
    val id: String,
    val externalId: ExternalId,
    val title: String,
    val summary: String,
    val duration: Double = 0.0,
    val durationLabel: String,
    val speakers: List<Speaker>,
    val thumbnail: Thumbnail,
    val link: String,
    @Contextual
    val publishedAt: Instant,
    val status: String,
)

@Serializable
data class MultiLangTitle(
    val japanese: String,
    val english: String,
)

@Serializable
data class MultiLangSummary(
    val japanese: String,
    val english: String,
)

@Serializable
data class AspectRatio(
    val width: Int = 0,
    val height: Int = 0,
)

@Serializable
data class Recording(
    val id: String,
    val externalId: ExternalId,
    @SerialName("title")
    val multiLangTitle: MultiLangTitle,
    @SerialName("summary")
    val multiLangSummary: MultiLangSummary,
    val thumbnail: Thumbnail,
    val aspectRatio: AspectRatio,
    val link: String,
    @Contextual
    val publishedAt: Instant,
    val status: String,
    val language: String,
)

@Serializable
data class FeedsResponse(
    val status: String,
    val articles: List<Article>,
    val episodes: List<Episode>,
    val recordings: List<Recording>,
)
