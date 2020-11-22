package io.github.droidkaigi.confsched2021.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.NewsContents
import io.github.droidkaigi.confsched2021.news.Filters
import kotlinx.coroutines.flow.StateFlow

interface INewsViewModel {
    val filter: StateFlow<Filters>
    val newsContents: StateFlow<NewsContents>
    fun onFilterChanged(filters: Filters)
    fun onToggleFavorite(article: News)
}

private val NewsViewModelAmbient = ambientOf<INewsViewModel>()

@Composable
fun ProvideNewsViewModel(viewModel: INewsViewModel, block: @Composable () -> Unit) {
    Providers(NewsViewModelAmbient provides viewModel, children = block)
}

@Composable
fun newsViewModel() = NewsViewModelAmbient.current
