package io.github.droidkaigi.feeder.data

import com.russhwolf.settings.MockSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import io.github.droidkaigi.feeder.Theme
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

    fun favoritesTimetable(): Flow<Set<String>> {
        return flowSettings
            .getStringFlow(KEY_FAVORITES_TIMETABLE)
            .map { favorites -> favorites.split(",").filter { it.isNotBlank() }.toSet() }
    }

    suspend fun addFavoriteTimetable(id: String) {
        flowSettings.putString(
            KEY_FAVORITES_TIMETABLE,
            (favoritesTimetable().first() + id).toSet().joinToString(","),
        )
    }

    suspend fun removeFavoriteTimetable(id: String) {
        flowSettings.putString(
            KEY_FAVORITES_TIMETABLE,
            (favoritesTimetable().first() - id).toSet().joinToString(","),
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

    fun deviceId(): Flow<String?> {
        return flowSettings.getStringOrNullFlow(KEY_DEVICE_ID)
    }

    suspend fun setDeviceId(deviceId: String) {
        flowSettings.putString(
            KEY_DEVICE_ID,
            deviceId
        )
    }

    private val mutableIdToken = MutableStateFlow<String?>(null)
    val idToken: StateFlow<String?> = mutableIdToken
    suspend fun setIdToken(token: String) = mutableIdToken.emit(token)

    fun theme(): Flow<Theme?> {
        return flowSettings.getStringOrNullFlow(KEY_THEME).map { it?.let { Theme.valueOf(it) } }
    }

    suspend fun changeTheme(theme: Theme) {
        flowSettings.putString(
            KEY_THEME,
            theme.name
        )
    }

    companion object {
        private const val KEY_FAVORITES = "KEY_FAVORITES"
        private const val KEY_FAVORITES_TIMETABLE = "KEY_FAVORITES_TIMETABLE"
        private const val KEY_AUTHENTICATED = "KEY_AUTHENTICATED"
        private const val KEY_DEVICE_ID = "KEY_DEVICE_ID"
        private const val KEY_THEME = "KEY_THEME"
    }
}

fun fakeUserDataStore() = object : UserDataStore() {
    override val flowSettings: FlowSettings = MockSettings().toFlowSettings()
}
