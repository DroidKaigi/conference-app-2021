package io.github.droidkaigi.feeder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.feeder.DroidKaigiAppViewModel
import io.github.droidkaigi.feeder.repository.ThemeRepository
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class RealDroidKaigiAppViewModel @Inject constructor(
    private val repository: ThemeRepository,
) : ViewModel(), DroidKaigiAppViewModel {
    private val effectChannel = Channel<DroidKaigiAppViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<DroidKaigiAppViewModel.Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<DroidKaigiAppViewModel.State> =
        repository.theme().map { DroidKaigiAppViewModel.State(theme = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = DroidKaigiAppViewModel.State()
            )

    override fun event(event: DroidKaigiAppViewModel.Event) {}
}
