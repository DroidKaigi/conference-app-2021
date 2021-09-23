package io.github.droidkaigi.feeder.data

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

actual class DriverFactory @Inject constructor(
    @ApplicationContext private val appContext: Context,
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(DestructiveMigrationSchema, appContext, "droidkaigi.db")
    }
}
