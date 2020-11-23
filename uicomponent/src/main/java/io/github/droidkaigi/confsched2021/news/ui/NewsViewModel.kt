package io.github.droidkaigi.confsched2021.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import io.github.droidkaigi.confsched2021.news.Filters
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.NewsContents
import io.github.droidkaigi.confsched2021.news.fakeNewsContents
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface INewsViewModel {
    val filter: StateFlow<Filters>
    val newsContents: StateFlow<NewsContents>
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
        override val filter: MutableStateFlow<Filters> = MutableStateFlow(Filters())
        override val newsContents: MutableStateFlow<NewsContents> = MutableStateFlow(
            fakeNewsContents()
        )

        override fun onFilterChanged(filters: Filters) {
            filter.value = filters
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
