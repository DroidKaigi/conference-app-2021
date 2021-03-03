package io.github.droidkaigi.feeder

enum class Lang {
    JA,
    EN;
}

fun defaultLang() = if (getDefaultLocale() == Locale.JAPAN) Lang.JA else Lang.EN
