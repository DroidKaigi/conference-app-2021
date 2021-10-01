package io.github.droidkaigi.feeder.data

import com.russhwolf.settings.MockSettings
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toFlowSettings
import io.github.droidkaigi.feeder.FeedItemId
import io.github.droidkaigi.feeder.Lang
import io.github.droidkaigi.feeder.Theme
import io.github.droidkaigi.feeder.TimetableItemId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

abstract class UserDataStore {
    protected abstract val flowSettings: FlowSettings
    fun favorites(): Flow<Set<FeedItemId>> {
        return flowSettings
            .getStringFlow(KEY_FAVORITES)
            .map { favorites ->
                favorites
                    .split(",")
                    .filter { it.isNotBlank() }
                    .map { FeedItemId(it) }
                    .toSet()
            }
    }

    suspend fun addFavorite(id: FeedItemId) {
        flowSettings.putString(
            KEY_FAVORITES,
            (favorites().first() + id).toSet().joinToString(","),
        )
    }

    suspend fun removeFavorite(id: FeedItemId) {
        flowSettings.putString(
            KEY_FAVORITES,
            (favorites().first() - id).toSet().joinToString(","),
        )
    }

    fun favoriteTimetableItemIds(): Flow<Set<TimetableItemId>> {
        return flowSettings
            .getStringFlow(KEY_FAVORITES_TIMETABLE_ITEM_ID)
            .map { favorites ->
                favorites
                    .split(",")
                    .filter { it.isNotBlank() }
                    .map { TimetableItemId(it) }
                    .toSet()
            }
    }

    suspend fun addFavoriteTimetableItemId(id: TimetableItemId) {
        flowSettings.putString(
            KEY_FAVORITES_TIMETABLE_ITEM_ID,
            (favoriteTimetableItemIds().first() + id).toSet().joinToString(","),
        )
    }

    suspend fun removeFavoriteTimetableItemId(id: TimetableItemId) {
        flowSettings.putString(
            KEY_FAVORITES_TIMETABLE_ITEM_ID,
            (favoriteTimetableItemIds().first() - id).toSet().joinToString(","),
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

    fun language(): Flow<Lang?> {
        return flowSettings.getStringOrNullFlow(KEY_LANGUAGE).map { it?.let { Lang.valueOf(it) } }
    }

    suspend fun changeLanguage(language: Lang) {
        flowSettings.putString(
            KEY_LANGUAGE,
            language.name
        )
    }

    companion object {
        private const val KEY_FAVORITES = "KEY_FAVORITES"
        private const val KEY_FAVORITES_TIMETABLE_ITEM_ID = "KEY_FAVORITES_TIMETABLE_ITEM_ID"
        private const val KEY_AUTHENTICATED = "KEY_AUTHENTICATED"
        private const val KEY_DEVICE_ID = "KEY_DEVICE_ID"
        private const val KEY_THEME = "KEY_THEME"
        private const val KEY_LANGUAGE = "KEY_LANGUAGE"
    }
}

fun fakeUserDataStore() = object : UserDataStore() {
    override val flowSettings: FlowSettings = MockSettings().toFlowSettings()
}
