package io.github.droidkaigi.feeder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.repository.TimetableRepository
import io.github.droidkaigi.feeder.timetable2021.TimetableDetailViewModel
import javax.annotation.meta.Exhaustive
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class RealTimetableDetailViewModel @Inject constructor(
    private val repository: TimetableRepository,
) : ViewModel(), TimetableDetailViewModel {

    private val effectChannel = Channel<TimetableDetailViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<TimetableDetailViewModel.Effect> = effectChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            refreshRepository()
        }
    }

    private val allTimetableContents: StateFlow<TimetableContents> = repository
        .timetableContents()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = TimetableContents(),
        )

    override val state: StateFlow<TimetableDetailViewModel.State> =
        allTimetableContents.map { contents ->
            TimetableDetailViewModel.State(
                timetableContents = contents
            )
        }.stateIn(
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

    private suspend fun refreshRepository() {
        try {
            repository.refresh()
        } catch (e: AppError) {
            effectChannel.send(TimetableDetailViewModel.Effect.ErrorMessage(e))
        }
    }
}
