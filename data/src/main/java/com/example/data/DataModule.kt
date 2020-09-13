package com.example.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2021.news.data.Api
import io.github.droidkaigi.confsched2021.news.data.ArticlesRepository
import io.github.droidkaigi.confsched2021.news.data.DaggerApi
import io.github.droidkaigi.confsched2021.news.data.DaggerArticlesRepository
import io.github.droidkaigi.confsched2021.news.data.DataStoreUserStore
import io.github.droidkaigi.confsched2021.news.data.UserStore

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    internal fun provideApi(daggerApi: DaggerApi): Api {
        return daggerApi
    }

    @Provides
    internal fun provideUserStore(daggerUserStore: DataStoreUserStore): UserStore {
        return daggerUserStore
    }

    @Provides
    internal fun provideRepository(daggerRepository: DaggerArticlesRepository): ArticlesRepository {
        return daggerRepository
    }
}