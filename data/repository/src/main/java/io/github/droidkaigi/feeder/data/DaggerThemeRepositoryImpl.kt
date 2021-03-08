package io.github.droidkaigi.feeder.data

import io.github.droidkaigi.feeder.repository.ThemeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerThemeRepositoryImpl @Inject constructor(
    dataDataStore: UserDataStore,
) : ThemeRepositoryImpl(
    dataDataStore,
)
