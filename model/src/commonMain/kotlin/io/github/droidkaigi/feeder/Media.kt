package io.github.droidkaigi.feeder

sealed class Media(val text: String) {
    object YouTube : Media("YouTube")
    object DroidKaigiFM : Media("DroidKaigi.FM")
    object Medium : Media("Medium")
    object Conference : Media("Conference")
    object Other : Media("Other")

    companion object {
        fun parse(string: String): Media = when (string) {
            "YouTube" -> YouTube
            "DroidKaigi.FM" -> DroidKaigiFM
            "Medium" -> Medium
            "Conference" -> Conference
            else -> Other
        }
    }
}
