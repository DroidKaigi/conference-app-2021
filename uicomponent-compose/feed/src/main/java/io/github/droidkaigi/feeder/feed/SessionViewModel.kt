package io.github.droidkaigi.feeder.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.SessionContents
import io.github.droidkaigi.feeder.SessionItem
import io.github.droidkaigi.feeder.core.UnidirectionalViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SessionViewModel :
    UnidirectionalViewModel<SessionViewModel.Event, SessionViewModel.Effect, SessionViewModel
        .State> {
    data class State(
        val showProgress: Boolean = false,
        val sessionContents: SessionContents = SessionContents(),
    )

    sealed class Effect {
        data class ErrorMessage(val appError: AppError) : Effect()
    }

    sealed class Event {
        class ChangeFavoriteFilter(val filters: Filters) : Event()
        class ToggleFavorite(val feedItem: SessionItem) : Event()
        object ReloadContent : Event()
    }

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val LocalSessionViewModel = compositionLocalOf<@Composable () -> SessionViewModel> {
    {
        error("not LocalSessionViewModel provided")
    }
}

fun provideSessionViewModelFactory(viewModel: @Composable () -> SessionViewModel) =
    LocalSessionViewModel provides viewModel

@Composable
fun sessionViewModel() = LocalSessionViewModel.current()
