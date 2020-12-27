package io.github.droidkaigi.confsched2021.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.droidkaigi.confsched2021.news.data.NewsRepository
import io.github.droidkaigi.confsched2021.news.ui.news.NewsViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.annotation.meta.Exhaustive

class RealNewsViewModel @ViewModelInject constructor(
    private val repository: NewsRepository,
) : ViewModel(), NewsViewModel {

    private val allNewsContents: StateFlow<NewsContents> = repository.newsContents()
        .stateIn(viewModelScope, SharingStarted.Eagerly, NewsContents())
    private val filters: MutableStateFlow<Filters> = MutableStateFlow(Filters())

    override val state: StateFlow<NewsViewModel.State> =
        combine(allNewsContents, filters) { newsContents, filters ->
            val filteredNews = newsContents.filtered(filters)
            NewsViewModel.State(
                filters = filters,
                filteredNewsContents = filteredNews
            )
        }
            .stateIn(viewModelScope, SharingStarted.Eagerly, NewsViewModel.State())
    private val effectChannel = Channel<NewsViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<NewsViewModel.Effect> = effectChannel.receiveAsFlow()

    override fun event(event: NewsViewModel.Event) {
        viewModelScope.launch {
            @Exhaustive
            when (event) {
                is NewsViewModel.Event.ChangeFavoriteFilter -> {
                    filters.value = event.filters
                }
                is NewsViewModel.Event.ToggleFavorite -> {
                    if (allNewsContents.value.favorites.contains(event.news.id)) {
                        repository.removeFavorite(event.news)
                    } else {
                        repository.addFavorite(event.news)
                    }
                }
                is NewsViewModel.Event.OpenDetail -> {
                    effectChannel.send(NewsViewModel.Effect.OpenDetail(event.news))
                }
            }
        }
    }
}
