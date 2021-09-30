package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.Lang
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    suspend fun changeLanguage(language: Lang)

    fun language(): Flow<Lang?>
}
