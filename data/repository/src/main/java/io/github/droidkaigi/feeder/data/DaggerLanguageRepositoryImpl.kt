package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerLanguageRepositoryImpl @Inject constructor(
    dataDataStore: UserDataStore,
) : LanguageRepositoryImpl(
    dataDataStore,
)
