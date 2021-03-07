package io.github.droidkaigi.feeder

import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.currentLocale

actual fun getDefaultLocale(): Locale =
    if (NSLocale.currentLocale.countryCode == "JP") {
        Locale.JAPAN
    } else {
        Locale.OTHER
    }
