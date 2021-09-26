package io.github.droidkaigi.feeder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.LoadState
import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.core.util.ProgressTimeLatch
import io.github.droidkaigi.feeder.getContents
import io.github.droidkaigi.feeder.orEmptyContents
import io.github.droidkaigi.feeder.repository.TimetableRepository
import io.github.droidkaigi.feeder.timetable2021.TimetableDetailViewModel
import io.github.droidkaigi.feeder.timetable2021.TimetableViewModel
import io.github.droidkaigi.feeder.toLoadState
import javax.annotation.meta.Exhaustive
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class RealTimetableDetailViewModel @Inject constructor(
    private val repository: TimetableRepository,
) : ViewModel(), TimetableDetailViewModel {

    private val effectChannel = Channel<TimetableDetailViewModel.Effect>(Channel.UNLIMITED)
    private val showProgressLatch = ProgressTimeLatch(viewModelScope = viewModelScope)
    override val effect: Flow<TimetableDetailViewModel.Effect> = effectChannel.receiveAsFlow()

    private val allTimetableContents: StateFlow<LoadState<TimetableContents>> = repository
        .timetableContents()
        .toLoadState()
        .onEach { loadState ->
            if (loadState.isError()) {
                // FIXME: smartcast is not working
                val error = loadState as LoadState.Error
                error.getThrowableOrNull()?.printStackTrace()
                effectChannel.send(TimetableDetailViewModel.Effect.ErrorMessage(error.e))
            }
            showProgressLatch.refresh(loadState.isLoading())
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, LoadState.Loading)
    private val filters: MutableStateFlow<Filters> = MutableStateFlow(Filters())

    override val state: StateFlow<TimetableDetailViewModel.State> =
        combine(
            allTimetableContents,
            filters,
            showProgressLatch.toggleState,
        ) { feedContentsLoadState, _, _ ->
            val timetableContents =
                feedContentsLoadState.getValueOrNull().orEmptyContents()
            TimetableDetailViewModel.State(
                timetableContents = timetableContents
            )
        }
            .stateIn(
                scope = viewModelScope,
                // prefetch when splash screen
                started = SharingStarted.Eagerly,
                initialValue = TimetableDetailViewModel.State()
            )

    override fun event(event: TimetableDetailViewModel.Event) {
        viewModelScope.launch {
            @Exhaustive
            when (event) {
                is TimetableDetailViewModel.Event.ToggleFavorite -> {
                    val favorite = allTimetableContents.value
                        .getContents()
                        .favorites
                        .contains(event.timetableItem.id)
                    if (favorite) {
                        repository.removeFavorite(event.timetableItem.id)
                    } else {
                        repository.addFavorite(event.timetableItem.id)
                    }
                }
            }
        }
    }
}
