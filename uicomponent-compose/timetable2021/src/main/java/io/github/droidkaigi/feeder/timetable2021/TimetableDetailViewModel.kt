package io.github.droidkaigi.feeder.timetable2021

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.TimetableItem
import io.github.droidkaigi.feeder.core.UnidirectionalViewModel
import io.github.droidkaigi.feeder.fakeTimetableContents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TimetableDetailViewModel : UnidirectionalViewModel<
        TimetableDetailViewModel.Event,
        TimetableDetailViewModel.Effect,
        TimetableDetailViewModel.State,
        > {
    data class State(
        val timetableContents: TimetableContents = fakeTimetableContents(),
    )

    sealed class Effect {
        data class ErrorMessage(val appError: AppError) : Effect()
    }

    sealed class Event {
        class ToggleFavorite(val timetableItem: TimetableItem) : Event()
    }

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val LocalTimetableDetailViewModelFactory =
    compositionLocalOf<@Composable () -> TimetableDetailViewModel> {
        {
            error("not LocalFeedViewModelFactory provided")
        }
    }

fun provideTimetableDetailViewModelFactory(
    viewModelFactory: @Composable () -> TimetableDetailViewModel,
) = LocalTimetableDetailViewModelFactory provides viewModelFactory

@Composable
fun timetableDetailViewModel() = LocalTimetableDetailViewModelFactory.current()
