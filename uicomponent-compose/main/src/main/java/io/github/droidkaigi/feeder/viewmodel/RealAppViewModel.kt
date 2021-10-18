package io.github.droidkaigi.feeder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.feeder.AppViewModel
import io.github.droidkaigi.feeder.repository.LanguageRepository
import io.github.droidkaigi.feeder.repository.ThemeRepository
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class RealAppViewModel @Inject constructor(
    private val themeRepository: ThemeRepository,
    private val languageRepository: LanguageRepository,
) : ViewModel(), AppViewModel {
    private val effectChannel = Channel<AppViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<AppViewModel.Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<AppViewModel.State> =
        combine(themeRepository.theme(), languageRepository.language()) { theme, language ->
            AppViewModel.State(theme = theme, language = language)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = AppViewModel.State()
        )

    override fun event(event: AppViewModel.Event) {}
}
