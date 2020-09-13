package com.example.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.confsched2021.news.data.AndroidApi
import io.github.droidkaigi.confsched2021.news.data.Api

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    internal fun provideApi(androidApi: AndroidApi): Api {
        return androidApi
    }
}