package io.github.droidkaigi.confsched2021.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import io.github.droidkaigi.confsched2021.news.Article
import io.github.droidkaigi.confsched2021.news.Articles
import io.github.droidkaigi.confsched2021.news.Filters
import kotlinx.coroutines.flow.StateFlow

interface INewsViewModel {
    val filter: StateFlow<Filters>
    val articles: StateFlow<Articles>
    fun onFilterChanged(filters: Filters)
    fun toggleFavorite(article: Article)
}

private val NewsViewModelAmbient = ambientOf<INewsViewModel>()

@Composable
fun ProvideNewsViewModel(viewModel: INewsViewModel, block: @Composable() () -> Unit) {
    Providers(NewsViewModelAmbient provides viewModel, children = block)
}

@Composable
fun newsViewModel() = NewsViewModelAmbient.current
