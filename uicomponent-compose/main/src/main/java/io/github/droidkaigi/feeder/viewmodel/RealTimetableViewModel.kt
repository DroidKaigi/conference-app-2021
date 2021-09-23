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
class RealTimetableViewModel @Inject constructor(
    private val repository: TimetableRepository,
) : ViewModel(), TimetableViewModel {

    private val effectChannel = Channel<TimetableViewModel.Effect>(Channel.UNLIMITED)
    private val showProgressLatch = ProgressTimeLatch(viewModelScope = viewModelScope)
    override val effect: Flow<TimetableViewModel.Effect> = effectChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            refreshRepository()
        }
    }

    private val allTimetableContents: StateFlow<LoadState<TimetableContents>> = repository
        .timetableContents()
        .toLoadState()
        .onEach { loadState ->
            if (loadState.isError()) {
                // FIXME: smartcast is not working
                val error = loadState as LoadState.Error
                error.getThrowableOrNull()?.printStackTrace()
                effectChannel.send(TimetableViewModel.Effect.ErrorMessage(error.e))
            }
            showProgressLatch.refresh(loadState.isLoading())
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, LoadState.Loading)
    private val filters: MutableStateFlow<Filters> = MutableStateFlow(Filters())

    override val state: StateFlow<TimetableViewModel.State> =
        combine(
            allTimetableContents,
            filters,
            showProgressLatch.toggleState,
        ) { feedContentsLoadState, _, showProgress ->
            val timetableContents =
                feedContentsLoadState.getValueOrNull().orEmptyContents()
            TimetableViewModel.State(
                showProgress = showProgress,
                timetableContents = timetableContents
//                snackbarMessage = currentValue.snackbarMessage
            )
        }
            .stateIn(
                scope = viewModelScope,
                // prefetch when splash screen
                started = SharingStarted.Eagerly,
                initialValue = TimetableViewModel.State()
            )

    override fun event(event: TimetableViewModel.Event) {
        viewModelScope.launch {
            @Exhaustive
            when (event) {
                is TimetableViewModel.Event.ChangeFavoriteFilter -> {
                    filters.value = event.filters
                }
                is TimetableViewModel.Event.ToggleFavorite -> {
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
                is TimetableViewModel.Event.ReloadContent -> {
                    refreshRepository()
                }
            }
        }
    }

    private suspend fun refreshRepository() {
        try {
            repository.refresh()
        } catch (e: AppError) {
            effectChannel.send(TimetableViewModel.Effect.ErrorMessage(e))
        }
    }
}
