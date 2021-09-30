package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.Lang
import io.github.droidkaigi.feeder.repository.LanguageRepository
import kotlinx.coroutines.flow.Flow

open class LanguageRepositoryImpl(
    private val dataStore: UserDataStore,
) : LanguageRepository {
    override suspend fun changeLanguage(language: Lang) {
        dataStore.changeLanguage(language)
    }

    override fun language(): Flow<Lang?> {
        return dataStore.language()
    }
}
