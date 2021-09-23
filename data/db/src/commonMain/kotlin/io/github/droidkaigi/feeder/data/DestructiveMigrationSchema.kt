package io.github.droidkaigi.feeder.data

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.db.use

object DestructiveMigrationSchema : SqlDriver.Schema by Database.Schema {
    @OptIn(ExperimentalStdlibApi::class)
    override fun migrate(driver: SqlDriver, oldVersion: Int, newVersion: Int) {
        // https://github.com/cashapp/sqldelight/discussions/2476#discussioncomment-1040220
        val tables =
            driver.executeQuery(null, "SELECT name FROM sqlite_master WHERE type='table';", 0).use {
                buildList {
                    while (it.next()) {
                        val name = it.getString(0)!!
                        if (name != "sqlite_sequence" && name != "android_metadata") {
                            add(name)
                        }
                    }
                }
            }
        for (table in tables) {
            driver.execute(null, "DROP TABLE $table", 0)
        }
        Database.Schema.create(driver)
    }
}
