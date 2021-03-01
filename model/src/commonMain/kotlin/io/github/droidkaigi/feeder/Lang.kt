package io.github.droidkaigi.feeder

import java.util.Locale

fun defaultLang() = if (Locale.getDefault() == Locale.JAPAN) Lang.JA else Lang.EN
