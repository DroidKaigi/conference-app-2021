package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Authenticator
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.first

class AuthApi(
    private val httpClient: HttpClient,
    private val userDataStore: UserDataStore,
    private val authenticator: Authenticator
) {
    suspend fun authIfNeeded() {
        var idToken = authenticator.currentUser()?.idToken

        if (idToken == null) {
            // not authenticated
            idToken = authenticator.signInAnonymously()?.idToken.orEmpty()
        }
        userDataStore.setIdToken(idToken)

        if (userDataStore.isAuthenticated().first() == true) {
            return // Already registered on server
        }
        if (idToken.isBlank()) {
            return // Invalid id token
        }
        registerToServer(idToken)
        userDataStore.setAuthenticated(true)
    }

    private suspend fun registerToServer(createdIdToken: String) {
        runCatching {
            // Use httpClient for bypass auth process
            httpClient
                .post<String>("https://${BuildKonfig.API_END_PONT}/accounts") {
                    header(HttpHeaders.Authorization, "Bearer $createdIdToken")
                    contentType(ContentType.Application.Json)
                    body = "{}"
                }
        }.getOrElse {
            if (it !is ResponseException || it.response.status != HttpStatusCode.Conflict) {
                throw it
            }
        }
    }
}
