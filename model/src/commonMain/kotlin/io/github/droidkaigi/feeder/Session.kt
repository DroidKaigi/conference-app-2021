package io.github.droidkaigi.feeder

sealed class TimetableItem(
    open val title: MultiLangText,
) {
    data class Session(
        override val title: MultiLangText,
        val speakers: List<Speaker>,
    ) : TimetableItem(title)

    data class Special(
        override val title: MultiLangText,
        val speakers: List<Speaker> = listOf(),
    ) : TimetableItem(title)
}
