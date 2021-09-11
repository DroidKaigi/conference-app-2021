package io.github.droidkaigi.feeder

sealed class TimetableSlot(
    open val title: MultiLangText,
) {
    data class Session(
        override val title: MultiLangText,
        val speakers: List<Speaker>,
    ) : TimetableSlot(title)

    data class Special(
        override val title: MultiLangText,
        val speakers: List<Speaker> = listOf(),
    ) : TimetableSlot(title)
}
