package io.github.droidkaigi.feeder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedRepository
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.LoadState
import io.github.droidkaigi.feeder.feed.FeedViewModel
import io.github.droidkaigi.feeder.getContents
import io.github.droidkaigi.feeder.orEmptyContents
import io.github.droidkaigi.feeder.toLoadState
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
import javax.annotation.meta.Exhaustive
import javax.inject.Inject

@HiltViewModel
class RealFeedViewModel @Inject constructor(
    private val repository: FeedRepository,
) : ViewModel(), FeedViewModel {

    private val effectChannel = Channel<FeedViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<FeedViewModel.Effect> = effectChannel.receiveAsFlow()

    private val allFeedContents: StateFlow<LoadState<FeedContents>> = repository.newsContents()
        .toLoadState()
        .onEach { loadState ->
            if (loadState.isError()) {
                // FIXME: smartcast is not working
                val error = loadState as LoadState.Error
                error.getThrowableOrNull()?.printStackTrace()
                effectChannel.send(FeedViewModel.Effect.ErrorMessage(error.e))
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, LoadState.Loading)
    private val filters: MutableStateFlow<Filters> = MutableStateFlow(Filters())

    override val state: StateFlow<FeedViewModel.State> =
        combine(
            allFeedContents,
            filters
        ) { feedContentsLoadState, filters ->
            val filteredFeed =
                feedContentsLoadState.getValueOrNull().orEmptyContents().filtered(filters)
            FeedViewModel.State(
                showProgress = feedContentsLoadState.isLoading(),
                filters = filters,
                filteredFeedContents = filteredFeed,
//                snackbarMessage = currentValue.snackbarMessage
            )
        }
            .stateIn(
                scope = viewModelScope,
                // prefetch when splash screen
                started = SharingStarted.Eagerly,
                initialValue = FeedViewModel.State()
            )

    override fun event(event: FeedViewModel.Event) {
        viewModelScope.launch {
            @Exhaustive
            when (event) {
                is FeedViewModel.Event.ChangeFavoriteFilter -> {
                    filters.value = event.filters
                }
                is FeedViewModel.Event.ToggleFavorite -> {
                    val favorite = allFeedContents.value
                        .getContents()
                        .favorites
                        .contains(event.feedItem.id)
                    if (favorite) {
                        repository.removeFavorite(event.feedItem)
                    } else {
                        repository.addFavorite(event.feedItem)
                    }
                }
            }
        }
    }
}
