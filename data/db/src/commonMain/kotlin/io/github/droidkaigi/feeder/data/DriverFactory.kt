package io.github.droidkaigi.feeder.data

import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactory {
    fun createDriver(): SqlDriver
}
