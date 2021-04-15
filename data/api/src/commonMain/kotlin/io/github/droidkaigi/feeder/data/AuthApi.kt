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
        val currentUser = authenticator.currentUser()
        val isAuthenticated = userDataStore.isAuthenticated().first()
        val firebaseIdToken = currentUser?.idToken.orEmpty()
        if (isAuthenticated == true && firebaseIdToken.isNotBlank()) {
            // already authenticated
            userDataStore.setIdToken(firebaseIdToken)
            return
        }
        // not authenticated
        val user = authenticator.signInAnonymously()
        val createdIdToken = user?.idToken.orEmpty()
        userDataStore.setIdToken(createdIdToken)
        if (createdIdToken.isNotBlank()) {
            registerToServer(createdIdToken)
            userDataStore.setAuthenticated(true)
        }
    }

    private suspend fun registerToServer(createdIdToken: String) {
        runCatching {
            // Use httpClient for bypass auth process
            httpClient
                .post<String>("https://ssot-api-staging.an.r.appspot.com/accounts") {
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
