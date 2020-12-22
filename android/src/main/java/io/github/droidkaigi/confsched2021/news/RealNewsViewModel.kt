package io.github.droidkaigi.confsched2021.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.droidkaigi.confsched2021.news.data.NewsRepository
import io.github.droidkaigi.confsched2021.news.ui.news.NewsViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

    override fun onToggleFavorite(news: News) {
        viewModelScope.launch {
            if (allNewsContents.value.favorites.contains(news.id)) {
                repository.removeFavorite(news)
            } else {
                repository.addFavorite(news)
            }
        }
    }


    override fun onFilterChanged(filters: Filters) {
        this.filters.value = filters
    }
}
