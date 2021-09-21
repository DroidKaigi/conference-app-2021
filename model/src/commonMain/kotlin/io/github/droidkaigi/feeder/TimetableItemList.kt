package io.github.droidkaigi.feeder

data class TimetableItemList(
    val timetableItems: List<TimetableItem> = listOf(),
): List<TimetableItem> by timetableItems {
    fun getDayTimetableItems(day: DroidKaigi2021Day): TimetableItemList {
        return TimetableItemList(
            timetableItems.filter {
                it.startsAt in day.start..day.end
            }
        )
    }
}
