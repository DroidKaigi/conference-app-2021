package io.github.droidkaigi.feeder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.core.UnidirectionalViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DroidKaigiAppViewModel :
    UnidirectionalViewModel<
        DroidKaigiAppViewModel.Event,
        DroidKaigiAppViewModel.Effect,
        DroidKaigiAppViewModel.State> {
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

private val LocalDroidKaigiAppViewModel = compositionLocalOf<DroidKaigiAppViewModel> {
    error("not LocalDroidKaigiAppViewModel provided")
}

@Composable
fun ProvideDroidKaigiAppViewModel(
    viewModel: DroidKaigiAppViewModel,
    block: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDroidKaigiAppViewModel provides viewModel, content = block)
}

@Composable
fun droidKaigiAppViewModel() = LocalDroidKaigiAppViewModel.current
