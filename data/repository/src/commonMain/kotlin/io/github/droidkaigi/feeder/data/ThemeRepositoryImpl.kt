package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Theme
import io.github.droidkaigi.feeder.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow

open class ThemeRepositoryImpl(
    private val dataStore: UserDataStore,
) : ThemeRepository {
    override suspend fun changeTheme(theme: Theme) {
        dataStore.changeTheme(theme)
    }

    override fun theme(): Flow<Theme?> {
        return dataStore.theme()
    }
}
