package io.github.droidkaigi.confsched2021.news.ui.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import io.github.droidkaigi.confsched2021.news.Filters
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.NewsContents
import io.github.droidkaigi.confsched2021.news.ui.UnidirectionalViewModel
import kotlinx.coroutines.flow.StateFlow

interface NewsViewModel :
    UnidirectionalViewModel<NewsViewModel.Event, NewsViewModel.State> {
    data class State(
        val showProgress: Boolean = false,
        val filters: Filters = Filters(),
        val filteredNewsContents: NewsContents = NewsContents(),
        val snackbarMessage: String? = null,
    )

    sealed class Event {
        class OnChangeFavoriteFilter(val filters: Filters) : Event()
        class OnToggleFavorite(val news: News) : Event()
        object OnHideSnackbarMessage : Event()
    }

    override val state: StateFlow<State>
    override fun event(event: Event)
}

private val AmbientNewsViewModel = ambientOf<NewsViewModel>()

@Composable
fun ProvideNewsViewModel(viewModel: NewsViewModel, block: @Composable () -> Unit) {
    Providers(AmbientNewsViewModel provides viewModel, content = block)
}

@Composable
fun newsViewModel() = AmbientNewsViewModel.current
