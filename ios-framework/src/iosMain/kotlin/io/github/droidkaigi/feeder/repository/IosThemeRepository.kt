package io.github.droidkaigi.feeder.repository

import io.github.droidkaigi.feeder.NonNullSuspendWrapper
import io.github.droidkaigi.feeder.NullableFlowWrapper
import io.github.droidkaigi.feeder.Theme

interface IosThemeRepository {
    fun changeTheme(theme: Theme): NonNullSuspendWrapper<Unit>
    fun theme(): NullableFlowWrapper<Theme?>
}

class IosThemeRepositoryImpl(
    private val themeRepository: ThemeRepository
) : IosThemeRepository {
    override fun changeTheme(theme: Theme): NonNullSuspendWrapper<Unit> {
        return NonNullSuspendWrapper {
            themeRepository.changeTheme(theme)
        }
    }

    override fun theme(): NullableFlowWrapper<Theme?> {
        return NullableFlowWrapper(
            themeRepository.theme()
        )
    }
}
