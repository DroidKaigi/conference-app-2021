package io.github.droidkaigi.feeder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.core.UnidirectionalViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AppViewModel :
    UnidirectionalViewModel<
        AppViewModel.Event,
        AppViewModel.Effect,
        AppViewModel.State> {
    data class State(
        val theme: Theme? = Theme.SYSTEM,
    )

    sealed class Effect {
        data class ErrorMessage(val appError: AppError) : Effect()
    }

    sealed class Event

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val LocalAppViewModel = compositionLocalOf<AppViewModel> {
    error("not LocalDroidKaigiAppViewModel provided")
}

@Composable
fun ProvideAppViewModel(
    viewModel: AppViewModel,
    block: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalAppViewModel provides viewModel, content = block)
}

@Composable
fun appViewModel() = LocalAppViewModel.current
