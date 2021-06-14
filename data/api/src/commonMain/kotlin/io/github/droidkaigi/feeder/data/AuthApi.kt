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
        var currentUser = authenticator.currentUser()

        if (currentUser == null) {
            // not authenticated
            currentUser = authenticator.signInAnonymously() ?: return
            registerToServer(currentUser.idToken)
        }

        userDataStore.setIdToken(currentUser.idToken)
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
