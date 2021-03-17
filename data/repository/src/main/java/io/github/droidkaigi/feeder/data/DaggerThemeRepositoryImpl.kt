package io.github.droidkaigi.feeder.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DaggerThemeRepositoryImpl @Inject constructor(
    dataDataStore: UserDataStore,
) : ThemeRepositoryImpl(
    dataDataStore,
)
