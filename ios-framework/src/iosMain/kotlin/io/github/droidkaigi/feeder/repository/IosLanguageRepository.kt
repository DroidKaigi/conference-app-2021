package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.NonNullSuspendWrapper
import io.github.droidkaigi.feeder.NullableFlowWrapper
import io.github.droidkaigi.feeder.Lang

interface IosLanguageRepository {
    fun changeLanguage(language: Lang): NonNullSuspendWrapper<Unit>
    fun language(): NullableFlowWrapper<Lang?>
}

class IosLanguageRepositoryImpl(
    private val languageRepository: LanguageRepository
) : IosLanguageRepository {
    override fun changeLanguage(language: Lang): NonNullSuspendWrapper<Unit> {
        return NonNullSuspendWrapper {
            languageRepository.changeLanguage(language)
        }
    }

    override fun language(): NullableFlowWrapper<Lang?> {
        return NullableFlowWrapper(
            languageRepository.language()
        )
    }
}
