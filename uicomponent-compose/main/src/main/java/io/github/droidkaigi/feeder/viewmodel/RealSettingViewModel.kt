package io.github.droidkaigi.feeder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.exhaustive.Exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.feeder.repository.ThemeRepository
import io.github.droidkaigi.feeder.setting.SettingViewModel
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
class RealSettingViewModel @Inject constructor(
    private val repository: ThemeRepository,
) : ViewModel(), SettingViewModel {
    private val effectChannel = Channel<SettingViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<SettingViewModel.Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<SettingViewModel.State> =
        repository.theme().map { SettingViewModel.State(theme = it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = SettingViewModel.State()
            )

    override fun event(event: SettingViewModel.Event) {
        viewModelScope.launch {
            @Exhaustive
            when (event) {
                is SettingViewModel.Event.ChangeTheme -> {
                    event.theme?.let { repository.changeTheme(theme = it) }
                }
            }
        }
    }
}
