package io.github.droidkaigi.feeder

import android.os.Parcel
import android.os.Parcelable
import kotlin.time.ExperimentalTime
import kotlinx.datetime.Instant
import kotlin.jvm.JvmInline

@OptIn(ExperimentalTime::class)
sealed class TimetableItem(
    open val id: TimetableItemId,
    open val title: MultiLangText,
    open val startsAt: Instant,
    open val endsAt: Instant,
) {
    data class Session(
        override val id: TimetableItemId,
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        val speakers: List<TimetableSpeaker>,
    ) : TimetableItem(id, title, startsAt, endsAt)

    data class Special(
        override val id: TimetableItemId,
        override val title: MultiLangText,
        override val startsAt: Instant,
        override val endsAt: Instant,
        val speakers: List<TimetableSpeaker> = listOf(),
    ) : TimetableItem(id, title, startsAt, endsAt)
}

@JvmInline
value class TimetableItemId(val value: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TimetableItemId> {
        override fun createFromParcel(parcel: Parcel): TimetableItemId {
            return TimetableItemId(parcel)
        }

        override fun newArray(size: Int): Array<TimetableItemId?> {
            return arrayOfNulls(size)
        }
    }
}
