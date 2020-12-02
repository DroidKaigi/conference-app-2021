package io.github.droidkaigi.confsched2021.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import io.github.droidkaigi.confsched2021.news.Filters
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.NewsContents
import io.github.droidkaigi.confsched2021.news.fakeNewsContents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

interface INewsViewModel {
    val filters: StateFlow<Filters>
    val filteredNewsContents: StateFlow<NewsContents>
    fun onFilterChanged(filters: Filters)
    fun onToggleFavorite(news: News)
}

private val NewsViewModelAmbient = ambientOf<INewsViewModel>()

@Composable
fun ProvideNewsViewModel(viewModel: INewsViewModel, block: @Composable () -> Unit) {
    Providers(NewsViewModelAmbient provides viewModel, children = block)
}

@Composable
fun newsViewModel() = NewsViewModelAmbient.current

fun fakeNewsViewModel(): INewsViewModel {
    return object : INewsViewModel {
        val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)
        override val filters: MutableStateFlow<Filters> = MutableStateFlow(Filters())
        private val newsContents = MutableStateFlow(
            fakeNewsContents()
        )
        override val filteredNewsContents: StateFlow<NewsContents> = newsContents
            .combine(filters){ contents, filter ->
                contents.filtered(filter)
            }
            .stateIn(coroutineScope, SharingStarted.Eagerly, fakeNewsContents())

        override fun onFilterChanged(filters: Filters) {
            this.filters.value = filters
        }

        override fun onToggleFavorite(news: News) {
            val value = newsContents.value
            val newFavorites = if (!value.favorites.contains(news.id)) {
                value.favorites + news.id
            } else {
                value.favorites - news.id
            }
            newsContents.value = value.copy(
                favorites = newFavorites
            )
        }
    }
}
