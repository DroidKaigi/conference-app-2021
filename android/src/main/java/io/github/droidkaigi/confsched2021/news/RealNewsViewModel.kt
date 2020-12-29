package io.github.droidkaigi.confsched2021.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.droidkaigi.confsched2021.news.data.NewsRepository
import io.github.droidkaigi.confsched2021.news.ui.news.NewsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.annotation.meta.Exhaustive

class RealNewsViewModel @ViewModelInject constructor(
    private val repository: NewsRepository,
) : ViewModel(), NewsViewModel {

    private val allNewsContents: StateFlow<LoadState<NewsContents>> = repository.newsContents()
        .toLoadStateIn(viewModelScope)
    private val filters: MutableStateFlow<Filters> = MutableStateFlow(Filters())

    override val state: StateFlow<NewsViewModel.State> =
        createState(
            flow1 = allNewsContents,
            flow2 = filters,
            initialValue = NewsViewModel.State()
        ) {
                currentValue,
                (newsContentsLoadState, filters),
            ->
            val filteredNews = newsContentsLoadState.getValueOrNull().orEmpty().filtered(filters)
            NewsViewModel.State(
                showProgress = newsContentsLoadState.isLoading,
                filters = filters,
                filteredNewsContents = filteredNews,
                snackbarMessage = currentValue.snackbarMessage
            )
        }
            .stateIn(viewModelScope, SharingStarted.Eagerly, NewsViewModel.State())

    fun <T1, T2, R> createState(
        flow1: Flow<T1>,
        flow2: Flow<T2>,
        initialValue: R,
        transform: suspend (currentValue: R, source: Pair<T1, T2>) -> R,
    ) = combine(flow = flow1, flow2 = flow2) { v1, v2 ->
        v1 to v2
    }.scan(initial = initialValue, operation = transform)
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = initialValue)

    override fun event(event: NewsViewModel.Event) {
        viewModelScope.launch {
            @Exhaustive
            when (event) {
                is NewsViewModel.Event.OnChangeFavoriteFilter -> {
                    filters.value = event.filters
                }
                is NewsViewModel.Event.OnToggleFavorite -> {
                    if (allNewsContents.value.getValueOrNull()
                            .orEmpty()
                            .favorites.contains(event.news.id)
                    ) {
                        repository.removeFavorite(event.news)
                    } else {
                        repository.addFavorite(event.news)
                    }
                }
                NewsViewModel.Event.OnHideSnackbarMessage -> {

                }
            }
        }
    }
}
