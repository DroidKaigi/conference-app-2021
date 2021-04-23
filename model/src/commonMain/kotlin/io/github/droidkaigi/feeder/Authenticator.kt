package io.github.droidkaigi.feeder

data class User(
    val idToken: String?
)

interface Authenticator {
    suspend fun currentUser(): User?
    suspend fun signInAnonymously(): User?
}
