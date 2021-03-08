package io.github.droidkaigi.feeder

import android.content.Context
import androidx.startup.Initializer

class LoggerInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Logger.init()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
