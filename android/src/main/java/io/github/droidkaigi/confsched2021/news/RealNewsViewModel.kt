package io.github.droidkaigi.confsched2021.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confsched2021.news.data.NewsRepository
import io.github.droidkaigi.confsched2021.news.ui.news.NewsViewModel
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
class RealNewsViewModel @Inject constructor(
    private val repository: NewsRepository,
) : ViewModel(), NewsViewModel {

    private val effectChannel = Channel<NewsViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<NewsViewModel.Effect> = effectChannel.receiveAsFlow()

    private val allNewsContents: StateFlow<LoadState<NewsContents>> = repository.newsContents()
        .toLoadState()
        .onEach { loadState ->
            if (loadState.isError()) {
                // FIXME: smartcast is not working
                val error = loadState as LoadState.Error
                effectChannel.send(NewsViewModel.Effect.ErrorMessage(error.e))
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, LoadState.Loading)
    private val filters: MutableStateFlow<Filters> = MutableStateFlow(Filters())

    override val state: StateFlow<NewsViewModel.State> =
        combine(
            allNewsContents,
            filters
        ) { newsContentsLoadState, filters ->
            val filteredNews =
                newsContentsLoadState.getValueOrNull().orEmptyContents().filtered(filters)
            NewsViewModel.State(
                showProgress = newsContentsLoadState.isLoading(),
                filters = filters,
                filteredNewsContents = filteredNews,
//                snackbarMessage = currentValue.snackbarMessage
            )
        }
            .stateIn(viewModelScope, SharingStarted.Eagerly, NewsViewModel.State())

    override fun event(event: NewsViewModel.Event) {
        viewModelScope.launch {
            @Exhaustive
            when (event) {
                is NewsViewModel.Event.ChangeFavoriteFilter -> {
                    filters.value = event.filters
                }
                is NewsViewModel.Event.ToggleFavorite -> {
                    val favorite = allNewsContents.value
                        .getContents()
                        .favorites
                        .contains(event.news.id)
                    if (favorite) {
                        repository.removeFavorite(event.news)
                    } else {
                        repository.addFavorite(event.news)
                    }
                }
            }
        }
    }
}
