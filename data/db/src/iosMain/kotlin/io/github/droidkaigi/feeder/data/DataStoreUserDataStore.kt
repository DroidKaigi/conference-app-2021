package io.github.droidkaigi.feeder.data

import com.russhwolf.settings.coroutines.toFlowSettings

class DataStoreUserDataStore constructor(name: String? = null) : UserDataStore() {
    private val appleSetting = AppleSettings.Factory().create(name)
    override val flowSettings = appleSetting.toFlowSettings()
}
