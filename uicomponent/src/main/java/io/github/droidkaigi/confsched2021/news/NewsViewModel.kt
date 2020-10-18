package io.github.droidkaigi.confsched2021.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
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
