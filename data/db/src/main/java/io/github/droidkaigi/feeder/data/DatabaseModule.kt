package io.github.droidkaigi.feeder.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    internal fun provideDatabase(driverFactory: DriverFactory): Database {
        val driver = driverFactory.createDriver()
        return Database(driver)
    }
}
