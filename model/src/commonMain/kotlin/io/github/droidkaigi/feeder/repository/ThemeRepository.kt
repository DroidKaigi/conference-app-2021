package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.Theme
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
    suspend fun changeTheme(theme: Theme)

    fun theme(): Flow<Theme?>
}
