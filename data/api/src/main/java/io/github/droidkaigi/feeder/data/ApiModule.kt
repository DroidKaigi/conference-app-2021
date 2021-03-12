package io.github.droidkaigi.feeder.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.okhttp.OkHttp
import javax.inject.Singleton
import okhttp3.Interceptor

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    internal fun provideNetworkService(
        userDataStore: UserDataStore,
        networkInterceptors: List<@JvmSuppressWildcards Interceptor>
    ): NetworkService {
        return NetworkService.create(
            engineFactory = OkHttp,
            userDataStore = userDataStore
        ) {
            networkInterceptors.forEach { addNetworkInterceptor(it) }
        }
    }

    @Singleton
    @Provides
    internal fun provideFirebaseAuthApi(
        networkService: NetworkService,
        userDataStore: UserDataStore,
    ): AuthApi {
        return AuthApi(networkService, userDataStore)
    }
}
