package io.github.droidkaigi.feeder.data

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Interceptor

@InstallIn(SingletonComponent::class)
@Module
class ApiDebugModule {
    @Singleton
    @Provides
    internal fun provideNetworkFlipperPlugin(): NetworkFlipperPlugin {
        return NetworkFlipperPlugin()
    }

    @Singleton
    @Provides
    internal fun provideOkHttpNetworkInterceptors(
        networkFlipperPlugin: NetworkFlipperPlugin,
    ): List<Interceptor> {
        return listOf(
            FlipperOkhttpInterceptor(networkFlipperPlugin),
        )
    }
}
