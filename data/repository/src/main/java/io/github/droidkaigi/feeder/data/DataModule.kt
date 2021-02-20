package io.github.droidkaigi.feeder.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.feeder.NewsRepository

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    internal fun provideApi(daggerApi: DaggerKtorNewsApi): NewsApi {
        return daggerApi
    }

    @Provides
    internal fun provideUserStore(daggerUserStore: DataStoreUserDataStore): UserDataStore {
        return daggerUserStore
    }

    @Provides
    internal fun provideRepository(daggerRepository: DaggerNewsRepositoryImpl): NewsRepository {
        return daggerRepository
    }
}
