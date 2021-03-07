package io.github.droidkaigi.feeder.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Interceptor

@InstallIn(SingletonComponent::class)
@Module
class ApiReleaseModule {
    @Singleton
    @Provides
    internal fun provideOkHttpNetworkInterceptors(): List<Interceptor> {
        return listOf()
    }
}
