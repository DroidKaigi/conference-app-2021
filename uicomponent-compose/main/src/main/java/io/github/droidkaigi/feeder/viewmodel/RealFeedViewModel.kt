package io.github.droidkaigi.feeder.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewModelScopeWithClock
import app.cash.molecule.launchMolecule
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.LoadState
import io.github.droidkaigi.feeder.core.util.ProgressTimeLatch
import io.github.droidkaigi.feeder.feed.FeedViewModel
import io.github.droidkaigi.feeder.getContents
import io.github.droidkaigi.feeder.orEmptyContents
import io.github.droidkaigi.feeder.repository.FeedRepository
import io.github.droidkaigi.feeder.toLoadState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.annotation.meta.Exhaustive
import javax.inject.Inject

@HiltViewModel
class RealFeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository,
) : ViewModel(), FeedViewModel {
    private val eventFlow = MutableSharedFlow<FeedViewModel.Event>()

    private val effectChannel = Channel<FeedViewModel.Effect>(Channel.UNLIMITED)
    private val showProgressLatch = ProgressTimeLatch(viewModelScope = viewModelScope)
    override val effect: Flow<FeedViewModel.Effect> = effectChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            refreshRepository()
        }
    }

    val flow = feedRepository.feedContents().toLoadState()

    @Composable
    fun contentsLoadState(): State<LoadState<FeedContents>> {
        return produceState<LoadState<FeedContents>>(initialValue = LoadState.Loading) {
            println("launch")
            feedRepository.feedContents()
                .catch { value = LoadState.Error(it) }
                .collect{
                    println("collect")
                    value = LoadState.Loaded(it)
                }
        }
    }

    override val state: StateFlow<FeedViewModel.State> = viewModelScopeWithClock.launchMolecule {
        val feedContentsLoadState by contentsLoadState()
        val showProgress by showProgressLatch.toggleState.collectAsState()
        var filters by remember { mutableStateOf(Filters()) }
        val filteredFeed by derivedStateOf {
            feedContentsLoadState.getValueOrNull().orEmptyContents()
                .filtered(filters)
        }

        LaunchedEffect(Unit) {
            eventFlow.collect { event ->
                @Exhaustive
                when (event) {
                    is FeedViewModel.Event.ChangeFavoriteFilter -> {
                        filters = event.filters
                    }
                    is FeedViewModel.Event.ToggleFavorite -> {
                        val favorite = feedContentsLoadState
                            .getContents()
                            .favorites
                            .contains(event.feedItem.id)
                        if (favorite) {
                            feedRepository.removeFavorite(event.feedItem.id)
                        } else {
                            feedRepository.addFavorite(event.feedItem.id)
                        }
                    }
                    is FeedViewModel.Event.ReloadContent -> {
                        refreshRepository()
                    }
                }
            }
        }
        LaunchedEffect(Unit) {
            snapshotFlow { feedContentsLoadState }
                .collect { loadState ->
                    if (loadState.isError()) {
                        // FIXME: smartcast is not working
                        val error = loadState as LoadState.Error
                        error.getThrowableOrNull()?.printStackTrace()
                        effectChannel.send(FeedViewModel.Effect.ErrorMessage(error.e))
                    }
                    showProgressLatch.refresh(loadState.isLoading())
                }
        }

        FeedViewModel.State(
            showProgress = showProgress,
            filters = filters,
            filteredFeedContents = filteredFeed,
        )
    }

    override fun event(event: FeedViewModel.Event) {
        viewModelScopeWithClock.launch {
            eventFlow.emit(event)
        }
    }

    private suspend fun refreshRepository() {
        try {
            feedRepository.refresh()
        } catch (e: AppError) {
            effectChannel.send(FeedViewModel.Effect.ErrorMessage(e))
        }
    }
}
