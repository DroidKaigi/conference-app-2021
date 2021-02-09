package io.github.droidkaigi.confnews2021.data.response

import kotlinx.datetime.Instant
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ExternalId(
    var value: String,
    var serviceName: String,
)

@Serializable
class Thumbnail(
    var smallUrl: String,
    var standardUrl: String,
    var largeUrl: String,
)

@Serializable
class Article(
    var id: String,
    var externalId: ExternalId,
    var title: String,
    var summary: String,
    var authorName: String,
    var authorUrl: String,
    var thumbnail: Thumbnail,
    var link: String,
    @Contextual
    var publishedAt: Instant,
    var status: String,
    var language: String,
)

@Serializable
class Speaker(
    var name: String,
    var iconUrl: String,
)

@Serializable
class Episode(
    var id: String,
    var externalId: ExternalId,
    var title: String,
    var summary: String,
    var duration: Double = 0.0,
    var durationLabel: String,
    var speakers: List<Speaker>,
    var thumbnail: Thumbnail,
    var link: String,
    @Contextual
    var publishedAt: Instant,
    var status: String,
)

@Serializable
class MultiLangTitle(
    var japanese: String,
    var english: String,
)

@Serializable
class MultiLangSummary(
    var japanese: String,
    var english: String,
)

@Serializable
class AspectRatio(
    var width: Int = 0,
    var height: Int = 0,
)

@Serializable
class Recording(
    var id: String,
    var externalId: ExternalId,
    @SerialName("title")
    var multiLangTitle: MultiLangTitle,
    @SerialName("summary")
    var multiLangSummary: MultiLangSummary,
    var thumbnail: Thumbnail,
    var aspectRatio: AspectRatio,
    var link: String,
    @Contextual
    var publishedAt: Instant,
    var status: String,
    var language: String,
)

@Serializable
class FeedsResponse(
    var status: String,
    var articles: List<Article>,
    var episodes: List<Episode>,
    var recordings: List<Recording>,
)
