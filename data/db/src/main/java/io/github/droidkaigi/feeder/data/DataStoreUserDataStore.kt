package io.github.droidkaigi.feeder.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.russhwolf.settings.datastore.DataStoreSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreUserDataStore @Inject constructor(
    @ApplicationContext val appContext: Context,
) : UserDataStore() {
    private val dataStore: DataStore<Preferences> = appContext.createDataStore(
        name = "user_preferences"
    )
    private val dataStoreSettings = DataStoreSettings(dataStore)
    override val flowSettings = dataStoreSettings
}
