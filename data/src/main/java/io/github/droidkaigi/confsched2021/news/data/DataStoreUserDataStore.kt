package io.github.droidkaigi.confsched2021.news.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesSetKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DataStoreUserDataStore @Inject constructor(
    @ApplicationContext val appContext: Context
) : UserDataStore {

    private val dataStore: DataStore<Preferences> = appContext.createDataStore(
        name = "user_preferences"
    )

    override fun favorites(): Flow<Set<String>> {
        return dataStore.data.map { currentPreferences ->
            currentPreferences[preferencesSetKey(FAVORITES)] ?: setOf()
        }
    }

    override suspend fun addFavorite(id: String) {
        dataStore.edit { preferences ->
            val current: Set<String> = preferences[preferencesSetKey(FAVORITES)] ?: setOf()
            val added = current.toMutableSet().apply {
                add(id)
            }
            preferences[preferencesSetKey(FAVORITES)] = added
        }
    }

    override suspend fun removeFavorite(id: String) {
        dataStore.edit { preferences ->
            val current: Set<String> = preferences[preferencesSetKey(FAVORITES)] ?: setOf<String>()
            val removed = current.toMutableSet().apply {
                remove(id)
            }
            preferences[preferencesSetKey(FAVORITES)] = removed
        }
    }

    companion object {
        private const val FAVORITES = "KEY_FAVORITES"
    }
}