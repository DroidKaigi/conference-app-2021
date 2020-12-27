package io.github.droidkaigi.confsched2021.news.ui.news

import app.cash.exhaustive.Exhaustive
import io.github.droidkaigi.confsched2021.news.Filters
import io.github.droidkaigi.confsched2021.news.fakeNewsContents
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
    private val effectChannel = Channel<NewsViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<NewsViewModel.Effect> = effectChannel.receiveAsFlow()

    override fun event(event: NewsViewModel.Event) {
        coroutineScope.launch {
            @Exhaustive
            when (event) {
                is NewsViewModel.Event.ChangeFavoriteFilter -> {
                    filters.value = event.filters
                }
                is NewsViewModel.Event.ToggleFavorite -> {
                    val value = newsContents.value
                    val newFavorites = if (!value.favorites.contains(event.news.id)) {
                        value.favorites + event.news.id
                    } else {
                        value.favorites - event.news.id
                    }
                    newsContents.value = value.copy(
                        favorites = newFavorites
                    )
                }
                is NewsViewModel.Event.OpenDetail -> {
                    effectChannel.send(NewsViewModel.Effect.OpenDetail(event.news))
                }
            }
        }
    }
}
