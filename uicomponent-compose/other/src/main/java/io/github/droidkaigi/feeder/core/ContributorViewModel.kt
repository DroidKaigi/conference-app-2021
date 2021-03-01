package io.github.droidkaigi.feeder.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Contributor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ContributorViewModel:
    UnidirectionalViewModel<ContributorViewModel.Event, ContributorViewModel.Effect, ContributorViewModel.State> {
    data class State(
        val showProgress: Boolean = false,
        val contributorContents: List<Contributor> = emptyList()
    )

    sealed class Effect {
        data class ErrorMessage(val apiError: AppError): Effect()
    }

    sealed class Event

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val LocalContributeViewModel = compositionLocalOf<ContributorViewModel> {
    error("Not view model provided")
}

@Composable
fun ProvideFeedViewModel(viewModel: ContributorViewModel, block: @Composable () -> Unit) {
    CompositionLocalProvider(LocalContributeViewModel provides viewModel, content = block)
}

@Composable
fun contributeViewModel() = LocalContributeViewModel.current
