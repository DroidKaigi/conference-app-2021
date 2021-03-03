package io.github.droidkaigi.feeder

actual fun getDefaultLocale(): Locale =
    if (java.util.Locale.getDefault() == java.util.Locale.JAPAN) {
        Locale.JAPAN
    } else {
        Locale.OTHER
    }
