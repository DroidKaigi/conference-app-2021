package io.github.droidkaigi.feeder

import java.util.Locale

enum class Lang {
    JA,
    EN;
}

fun defaultLang() = if (Locale.getDefault() == Locale.JAPAN) Lang.JA else Lang.EN
