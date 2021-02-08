package io.github.droidkaigi.confnews2021.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import io.github.droidkaigi.confnews2021.AppError

class FirebaseAuthApi {
    suspend fun user(): FirebaseUser {
        val auth = Firebase.auth
        val currentUser = auth.currentUser
        println("currentUser:$currentUser")
        if (currentUser != null) {
            return currentUser
        }
        val result = auth.signInAnonymously()
        println("signin:${result.user}")
        return result.user ?: throw AppError.ApiException.NetworkException(IllegalStateException())
    }
}
