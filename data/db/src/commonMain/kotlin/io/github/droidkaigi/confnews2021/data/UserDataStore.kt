package io.github.droidkaigi.confnews2021.data

import com.russhwolf.settings.MockSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow
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

    fun idToken(): Flow<String?> {
        return flowSettings.getStringOrNullFlow(KEY_AUTH_ID_TOKEN)
    }

    suspend fun setAuthIdToken(token: String) {
        flowSettings.putString(
            KEY_FAVORITES,
            token,
        )
    }

    companion object {
        private const val KEY_FAVORITES = "KEY_FAVORITES"
        private const val KEY_AUTH_ID_TOKEN = "KEY_AUTH_ID_TOKEN"
    }
}

fun fakeUserDataStore() = object : UserDataStore() {
    override val flowSettings: FlowSettings = MockSettings().toFlowSettings()
}
