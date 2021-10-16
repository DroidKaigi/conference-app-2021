package io.github.droidkaigi.feeder

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import io.github.droidkaigi.feeder.db.BuildConfig

class LoggerInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Logger.init(
            if (BuildConfig.DEBUG) {
                DebugAntilog()
            } else {
                object : Antilog() {
                    override fun performLog(
                        priority: LogLevel,
                        tag: String?,
                        throwable: Throwable?,
                        message: String?,
                    ) {
                        // send only error log
                        if (priority < LogLevel.ERROR) return

                        throwable?.let {
                            when (it) {
                                // e.g. http exception, add a customized your exception message
//                is KtorException -> {
//                    FirebaseCrashlytics.getInstance()
//                        .log("${priority.ordinal}, HTTP Exception, ${it.response?.errorBody}")
//                }
                                else -> FirebaseCrashlytics.getInstance().recordException(it)
                            }
                        }
                    }
                }
            }
        )
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
