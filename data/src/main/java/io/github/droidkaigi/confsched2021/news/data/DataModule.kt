package io.github.droidkaigi.confsched2021.news.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    internal fun provideApi(daggerApi: DaggerKtorNewsApi): NewsApi {
        return daggerApi
    }

    @Provides
    internal fun provideUserStore(daggerUserStore: DataStoreUserDataStore): UserDataStore {
        return fakeUserDataStore()
    }

    @Provides
    internal fun provideRepository(daggerRepository: DaggerArticleRepository): ArticleRepository {
        return daggerRepository
    }
}