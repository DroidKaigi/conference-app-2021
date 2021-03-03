package io.github.droidkaigi.feeder

enum class Locale {
    JAPAN,
    OTHER
}

expect fun getDefaultLocale(): Locale
