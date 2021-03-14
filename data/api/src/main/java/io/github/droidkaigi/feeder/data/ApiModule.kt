package io.github.droidkaigi.feeder.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import javax.inject.Singleton
import okhttp3.Interceptor

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    internal fun provideHttpClient(
        userDataStore: UserDataStore,
        networkInterceptors: List<@JvmSuppressWildcards Interceptor>,
    ): HttpClient {
        return ApiHttpClient.create(
            engineFactory = OkHttp,
            userDataStore = userDataStore
        ) {
            networkInterceptors.forEach { addNetworkInterceptor(it) }
        }
    }

    @Singleton
    @Provides
    internal fun provideNetworkService(
        httpClient: HttpClient,
        authApi: AuthApi,
    ): NetworkService {
        return NetworkService(httpClient, authApi)
    }

    @Singleton
    @Provides
    internal fun provideFirebaseAuthApi(
        httpClient: HttpClient,
        userDataStore: UserDataStore,
    ): AuthApi {
        return AuthApi(httpClient, userDataStore)
    }
}
