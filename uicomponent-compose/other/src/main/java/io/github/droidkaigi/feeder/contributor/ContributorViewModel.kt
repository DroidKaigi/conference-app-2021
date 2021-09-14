package io.github.droidkaigi.feeder.contributor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.core.UnidirectionalViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ContributorViewModel :
    UnidirectionalViewModel<
        ContributorViewModel.Event,
        ContributorViewModel.Effect,
        ContributorViewModel.State> {

    data class State(
        val showProgress: Boolean = false,
        val contributorContents: List<Contributor> = emptyList(),
    )

    sealed class Effect {
        data class ErrorMessage(val apiError: AppError) : Effect()
    }

    sealed class Event

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val LocalContributeViewModelFactory =
    compositionLocalOf<@Composable () -> ContributorViewModel> {
        {
            error("Not view model provided")
        }
    }

fun provideContributorViewModelFactory(viewModelFactory: @Composable () -> ContributorViewModel) =
    LocalContributeViewModelFactory provides viewModelFactory

@Composable
fun contributeViewModel() = LocalContributeViewModelFactory.current()
