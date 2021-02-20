package io.github.droidkaigi.feeder.data

import com.russhwolf.settings.MockSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

abstract class UserDataStore {
    protected abstract val flowSettings: FlowSettings
    fun favorites(): Flow<Set<String>> {
        return flowSettings
            .getStringFlow(KEY_FAVORITES)
            .map { favorites -> favorites.split(",").filter { it.isNotBlank() }.toSet() }
    }

    suspend fun addFavorite(id: String) {
        flowSettings.putString(
            KEY_FAVORITES,
            (favorites().first() + id).toSet().joinToString(","),
        )
    }

    suspend fun removeFavorite(id: String) {
        flowSettings.putString(
            KEY_FAVORITES,
            (favorites().first() - id).toSet().joinToString(","),
        )
    }

    fun isAuthenticated(): Flow<Boolean?> {
        return flowSettings.getBooleanOrNullFlow(KEY_AUTHENTICATED)
    }

    suspend fun setAuthenticated(authenticated: Boolean) {
        flowSettings.putBoolean(
            KEY_AUTHENTICATED,
            authenticated,
        )
    }

    private val mutableIdToken = MutableStateFlow<String?>(null)
    val idToken: StateFlow<String?> = mutableIdToken
    suspend fun setIdToken(token: String) = mutableIdToken.emit(token)

    companion object {
        private const val KEY_FAVORITES = "KEY_FAVORITES"
        private const val KEY_AUTHENTICATED = "KEY_AUTHENTICATED"
    }
}

fun fakeUserDataStore() = object : UserDataStore() {
    override val flowSettings: FlowSettings = MockSettings().toFlowSettings()
}
