package io.github.droidkaigi.confsched2021.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.droidkaigi.confsched2021.news.data.NewsRepository
import io.github.droidkaigi.confsched2021.news.ui.INewsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel(), INewsViewModel {

    private val allNewsContents: Flow<NewsContents> = repository.newsContents()
    override val filter: MutableStateFlow<Filters> = MutableStateFlow(Filters())
    override val filteredNewsContents: StateFlow<NewsContents> = allNewsContents
        .combine(filter) { newsContents, filters ->
            newsContents.filtered(filters)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, NewsContents())

    override fun onFilterChanged(filters: Filters) {
        filter.value = filters
    }

    override fun onToggleFavorite(news: News) {
        viewModelScope.launch {
            if (filteredNewsContents.value.favorites.contains(news.id)) {
                repository.removeFavorite(news)
            } else {
                repository.addFavorite(news)
            }
        }
    }
}
