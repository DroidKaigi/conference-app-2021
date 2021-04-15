package io.github.droidkaigi.feeder.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import io.github.droidkaigi.feeder.Authenticator
import io.github.droidkaigi.feeder.Logger
import io.github.droidkaigi.feeder.User
import javax.inject.Inject

class AuthenticatorImpl @Inject constructor(): Authenticator {

    override suspend fun currentUser() : User? {
        val firebaseUser = Firebase.auth.currentUser ?: return null
        val idToken = firebaseUser.getIdToken(false)

        return User(idToken)
    }

    override suspend fun signInAnonymously() : User? {
        val result = Firebase.auth.signInAnonymously()
        Logger.d("signin:${result.user}")

        val firebaseUser = result.user ?: return null
        val idToken = firebaseUser.getIdToken(false)

        return User(idToken)
    }

}
