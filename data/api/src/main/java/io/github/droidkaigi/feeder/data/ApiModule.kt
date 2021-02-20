package io.github.droidkaigi.feeder.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.droidkaigi.feeder.data.response.InstantSerializer
import io.ktor.client.HttpClient
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.headers
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    @Provides
    internal fun provideHttpClient(userDataStore: UserDataStore): HttpClient {
        return HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    json = Json {
                        serializersModule = SerializersModule {
                            contextual(InstantSerializer)
                        }
                    }
                )
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                headers {
                    userDataStore.idToken.value?.let {
                        set("Authorization", "Bearer $it")
                    }
                }
            }
        }
    }

    @Provides
    internal fun provideFirebaseAuthApi(
        httpClient: HttpClient,
        userDataStore: UserDataStore,
    ): AuthApi {
        return AuthApi(httpClient, userDataStore)
    }
}
