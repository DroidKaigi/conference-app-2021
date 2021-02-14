package io.github.droidkaigi.confnews2021.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import io.github.droidkaigi.confnews2021.AppError
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.cio.Response
import io.ktor.http.contentType
import kotlinx.coroutines.flow.first

class UserApi(
    val httpClient: HttpClient,
    val userDataStore: UserDataStore,
) {
    /**
     * @return auth id token
     */
    suspend fun authIfNeeded(): String {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        val savedIdToken = userDataStore.idToken().first()
        val firebaseIdToken = currentUser?.getIdToken(false)
        if (savedIdToken.orEmpty().isNotBlank() && firebaseIdToken == savedIdToken) {
            // already authenticated
            return savedIdToken.orEmpty()
        }
        if (firebaseIdToken.orEmpty().isNotBlank()) {
            // authenticated. but not registered in server
            val nonNullFirebaseIdToken = firebaseIdToken.orEmpty()
            registerToServer(nonNullFirebaseIdToken)
            userDataStore.setAuthIdToken(nonNullFirebaseIdToken)
            return nonNullFirebaseIdToken
        }
        // not authenticated
        val result = auth.signInAnonymously()
        println("signin:${result.user}")
        val createdIdToken = result.user?.getIdToken(false).orEmpty()
        if (createdIdToken.isNotBlank()) {
            registerToServer(createdIdToken)
            userDataStore.setAuthIdToken(createdIdToken)
        }
        return createdIdToken
            ?: throw AppError.ApiException.NetworkException(IllegalStateException("can not auth"))
    }

    private suspend fun registerToServer(createdIdToken: String) {
        httpClient.post<String>("https://ssot-api-staging.an.r.appspot.com/accounts") {
            headers {
                set("Authorization", "Bearer $createdIdToken")
            }
            contentType(ContentType.Application.Json)
            body = "{}"
        }
    }
}
