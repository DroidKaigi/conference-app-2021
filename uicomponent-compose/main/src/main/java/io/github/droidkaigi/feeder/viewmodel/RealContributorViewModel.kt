package io.github.droidkaigi.feeder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.LoadState
import io.github.droidkaigi.feeder.contributor.ContributorViewModel
import io.github.droidkaigi.feeder.repository.ContributorRepository
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
class RealContributorViewModel @Inject constructor(
    contributorRepository: ContributorRepository,
) : ViewModel(), ContributorViewModel {

    private val effectChannel = Channel<ContributorViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<ContributorViewModel.Effect> = effectChannel.receiveAsFlow()

    private val contributorsContents: StateFlow<LoadState<List<Contributor>>> =
        contributorRepository.contributorContents().toLoadState()
            .onEach { loadState ->
                if (loadState.isError()) {
                    val error = loadState as LoadState.Error
                    error.getThrowableOrNull()?.printStackTrace()
                    effectChannel.send(ContributorViewModel.Effect.ErrorMessage(error.e))
                }
            }.stateIn(viewModelScope, SharingStarted.Lazily, LoadState.Loading)

    override val state: StateFlow<ContributorViewModel.State> =
        contributorsContents.map {
            val contributor = it.getValueOrNull() ?: emptyList()
            ContributorViewModel.State(false, contributor)
        }.stateIn(viewModelScope, SharingStarted.Lazily, ContributorViewModel.State())

    override fun event(event: ContributorViewModel.Event) {}
}
