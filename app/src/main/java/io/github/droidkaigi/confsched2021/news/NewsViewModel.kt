package io.github.droidkaigi.confsched2021.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.droidkaigi.confsched2021.news.data.ArticleRepository
import io.github.droidkaigi.confsched2021.news.ui.INewsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel @ViewModelInject constructor(
    private val repository: ArticleRepository
) : ViewModel(), INewsViewModel {
    private val allNewsContents: Flow<NewsContents> = repository.articles()
    override val filter: MutableStateFlow<Filters> = MutableStateFlow(Filters())
    override val newsContents: StateFlow<NewsContents> = allNewsContents
        .combine(filter) { articles, filters ->
            articles.filtered(filters)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, NewsContents())

    override fun onFilterChanged(filters: Filters) {
        filter.value = filters
    }

    override fun onToggleFavorite(article: News) {
        viewModelScope.launch {
            if (newsContents.value.favorites.contains(article)) {
                repository.removeFavorite(article)
            } else {
                repository.addFavorite(article)
            }
        }
    }
}
