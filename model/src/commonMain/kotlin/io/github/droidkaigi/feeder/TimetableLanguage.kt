package io.github.droidkaigi.feeder

typealias TimetableLanguage = String

fun TimetableLanguage.currentLangTitle(): String {
    return when (defaultLang()) {
        Lang.JA -> when (this) {
            "JAPANESE" -> "日本語"
            "ENGLISH" -> "英語"
            else -> "未定"
        }
        Lang.EN -> this
    }
}
