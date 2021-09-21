package io.github.droidkaigi.feeder.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Theme
import io.github.droidkaigi.feeder.core.UnidirectionalViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SettingViewModel :
    UnidirectionalViewModel<
        SettingViewModel.Event,
        SettingViewModel.Effect,
        SettingViewModel.State> {
    data class State(
        val theme: Theme? = Theme.SYSTEM,
    )

    sealed class Effect {
        data class ErrorMessage(val appError: AppError) : Effect()
    }

    sealed class Event {
        class ChangeTheme(val theme: Theme?) : Event()
    }

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val LocalSettingViewModelFactory =
    compositionLocalOf<@Composable () -> SettingViewModel> {
        {
            error("not LocalSettingViewModelFactory provided")
        }
    }

fun provideSettingViewModelFactory(viewModelFactory: @Composable () -> SettingViewModel) =
    LocalSettingViewModelFactory provides viewModelFactory

@Composable
fun settingViewModel() = LocalSettingViewModelFactory.current()
