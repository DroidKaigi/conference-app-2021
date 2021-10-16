package io.github.droidkaigi.feeder

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

object Logger {
    fun init(antilog: Antilog = DebugAntilog()) {
        // for future changes
        Napier.base(antilog)
    }

    fun d(message: String) {
        Napier.d(message)
    }

    fun d(e: Throwable, message: String) {
        Napier.d(throwable = e, message = message)
    }
}
