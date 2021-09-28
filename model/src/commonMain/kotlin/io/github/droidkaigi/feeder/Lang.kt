package io.github.droidkaigi.feeder

enum class Lang {
    SYSTEM,
    JA,
    EN;
}

fun defaultLang() = if (getDefaultLocale() == Locale.JAPAN) Lang.JA else Lang.EN
