package io.github.droidkaigi.feeder.timetable2021

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.TimetableItem
import io.github.droidkaigi.feeder.core.UnidirectionalViewModel
import io.github.droidkaigi.feeder.fakeTimetableContents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TimetableViewModel :
    UnidirectionalViewModel<TimetableViewModel.Event, TimetableViewModel.Effect, TimetableViewModel
        .State> {
    data class State(
        val showProgress: Boolean = false,
        val timetableContents: TimetableContents = fakeTimetableContents(),
    )

    sealed class Effect {
        data class ErrorMessage(val appError: AppError) : Effect()
    }

    sealed class Event {
        class ChangeFavoriteFilter(val filters: Filters) : Event()
        class ToggleFavorite(val session: TimetableItem.Session) : Event()
        object ReloadContent : Event()
    }

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val LocalSessionViewModel = compositionLocalOf<@Composable () -> TimetableViewModel> {
    {
        error("not LocalSessionViewModel provided")
    }
}

fun provideTimetableViewModelFactory(viewModel: @Composable () -> TimetableViewModel) =
    LocalSessionViewModel provides viewModel

@Composable
fun sessionViewModel() = LocalSessionViewModel.current()
