package io.github.droidkaigi.confsched2021.news.ui.news

import io.github.droidkaigi.confsched2021.news.Filters
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.fakeNewsContents
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlin.coroutines.CoroutineContext

fun fakeNewsViewModel(): FakeNewsViewModel {
    return FakeNewsViewModel()
}

class FakeNewsViewModel : NewsViewModel {
    private val coroutineScope = CoroutineScope(object : CoroutineDispatcher() {
        // for preview
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            block.run()
        }
    })
    private val newsContents = MutableStateFlow(
        fakeNewsContents()
    )
    private val filters: MutableStateFlow<Filters> = MutableStateFlow(Filters())

    override val state: StateFlow<NewsViewModel.State> =
        combine(newsContents, filters) { newsContents, filters ->
            val filteredNews = newsContents.filtered(filters)
            NewsViewModel.State(
                filters = filters,
                filteredNewsContents = filteredNews
            )
        }
            .stateIn(coroutineScope, SharingStarted.Eagerly, NewsViewModel.State())

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
