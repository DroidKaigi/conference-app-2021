package io.github.droidkaigi.feeder

import android.os.Parcel
import android.os.Parcelable
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.jvm.JvmInline

sealed class FeedItem {
    abstract val id: FeedItemId
    abstract val publishedAt: Instant
    abstract val image: Image
    abstract val media: Media
    abstract val title: MultiLangText
    abstract val summary: MultiLangText
    abstract val link: String

    data class Blog(
        override val id: FeedItemId,
        override val publishedAt: Instant,
        override val image: Image,
        override val media: Media,
        override val title: MultiLangText,
        override val summary: MultiLangText,
        override val link: String,
        val language: String,
        val author: Author,
    ) : FeedItem()

    data class Video(
        override val id: FeedItemId,
        override val publishedAt: Instant,
        override val image: Image,
        override val media: Media,
        override val title: MultiLangText,
        override val summary: MultiLangText,
        override val link: String,
    ) : FeedItem()

    data class Podcast(
        override val id: FeedItemId,
        override val publishedAt: Instant,
        override val image: Image,
        override val media: Media,
        override val title: MultiLangText,
        override val summary: MultiLangText,
        override val link: String,
        val speakers: List<Speaker>,
        val podcastLink: String
    ) : FeedItem()

    fun publishedDateString(): String {
        val localDate = publishedAt
            .toLocalDateTime(TimeZone.currentSystemDefault())
        return "${localDate.year}/${localDate.monthNumber}/${localDate.dayOfMonth}"
    }
}

@JvmInline
value class FeedItemId(val value: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FeedItemId> {
        override fun createFromParcel(parcel: Parcel): FeedItemId {
            return FeedItemId(parcel)
        }

        override fun newArray(size: Int): Array<FeedItemId?> {
            return arrayOfNulls(size)
        }
    }
}
