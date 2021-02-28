package io.github.droidkaigi.feeder.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.Logger
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.flow.first

class AuthApi(
    private val httpClient: HttpClient,
    private val userDataStore: UserDataStore,
) {
    suspend fun <T> authenticated(block: suspend () -> T): T {
        authIfNeeded()
        return block()
    }

    /**
     * @return auth id token
     */
    private suspend fun authIfNeeded() {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        val isAuthenticated = userDataStore.isAuthenticated().first()
        val firebaseIdToken = currentUser?.getIdToken(false).orEmpty()
        if (isAuthenticated == true && firebaseIdToken.isNotBlank()) {
            // already authenticated
            userDataStore.setIdToken(firebaseIdToken)
            return
        }
        // not authenticated
        val result = auth.signInAnonymously()
        println("signin:${result.user}")
        val createdIdToken = result.user?.getIdToken(false).orEmpty()
        userDataStore.setIdToken(createdIdToken)
        if (createdIdToken.isNotBlank()) {
            registerToServer(createdIdToken)
            userDataStore.setAuthenticated(true)
        }
    }

    private suspend fun registerToServer(createdIdToken: String) {
        runCatching {
            httpClient.post<String>("https://ssot-api-staging.an.r.appspot.com/accounts") {
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
