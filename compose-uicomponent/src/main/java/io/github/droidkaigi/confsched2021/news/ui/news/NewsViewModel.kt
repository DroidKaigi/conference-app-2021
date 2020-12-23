package io.github.droidkaigi.confsched2021.news.ui.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import io.github.droidkaigi.confsched2021.news.Filters
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.NewsContents
import kotlinx.coroutines.flow.StateFlow

interface NewsViewModel {
    data class State(
        val filters: Filters = Filters(),
        val filteredNewsContents: NewsContents = NewsContents(),
    )

    sealed class Intent {
        class ChangeFavoriteFilter(val filters: Filters) : Intent()
        class ToggleFavorite(val news: News) : Intent()
    }

    val state: StateFlow<State>
    fun intent(intent: Intent)
}

private val AmbientNewsViewModel = ambientOf<NewsViewModel>()

@Composable
fun ProvideNewsViewModel(viewModel: NewsViewModel, block: @Composable () -> Unit) {
    Providers(AmbientNewsViewModel provides viewModel, content = block)
}

@Composable
fun newsViewModel() = AmbientNewsViewModel.current

