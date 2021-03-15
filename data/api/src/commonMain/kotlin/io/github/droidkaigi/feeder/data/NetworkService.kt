package io.github.droidkaigi.feeder.data

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put

class NetworkService(val httpClient: HttpClient, val authApi: AuthApi) {

    suspend inline fun <reified T : Any> get(
        url: String,
        needAuth: Boolean = false,
    ): T = try {
        if (needAuth) {
            authApi.authIfNeeded()
        }
        httpClient.get(url)
    } catch (e: Throwable) {
        throw e.toAppError()
    }

    suspend inline fun <reified T> post(
        urlString: String,
        needAuth: Boolean = false,
        block: HttpRequestBuilder.() -> Unit = {},
    ): T = try {
        if (needAuth) {
            authApi.authIfNeeded()
        }
        httpClient.post(urlString, block)
    } catch (e: Throwable) {
        throw e.toAppError()
    }

    suspend inline fun <reified T> put(
        urlString: String,
        needAuth: Boolean = false,
        block: HttpRequestBuilder.() -> Unit = {},
    ): T = try {
        if (needAuth) {
            authApi.authIfNeeded()
        }
        httpClient.put(urlString, block)
    } catch (e: Throwable) {
        throw e.toAppError()
    }
}
