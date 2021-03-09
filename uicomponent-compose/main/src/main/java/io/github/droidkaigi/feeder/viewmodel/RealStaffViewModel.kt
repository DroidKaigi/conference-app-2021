package io.github.droidkaigi.feeder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.feeder.LoadState
import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.repository.StaffRepository
import io.github.droidkaigi.feeder.staff.StaffViewModel
import io.github.droidkaigi.feeder.toLoadState
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class RealStaffViewModel @Inject constructor(
    repository: StaffRepository,
) : ViewModel(), StaffViewModel {

    private val effectChannel = Channel<StaffViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<StaffViewModel.Effect> = effectChannel.receiveAsFlow()

    private val allStaffContents: StateFlow<LoadState<List<Staff>>> = repository.staffContents()
        .toLoadState()
        .onEach { loadState ->
            if (loadState.isError()) {
                // FIXME: smartcast is not working
                val error = loadState as LoadState.Error
                error.getThrowableOrNull()?.printStackTrace()
                effectChannel.send(StaffViewModel.Effect.ErrorMessage(error.e))
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, LoadState.Loading)

    override val state: StateFlow<StaffViewModel.State> = allStaffContents.map {
        StaffViewModel.State(it.isLoading(), it.getValueOrNull() ?: listOf())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = StaffViewModel.State()
    )
    override fun event(event: StaffViewModel.Event) {
    }
}
