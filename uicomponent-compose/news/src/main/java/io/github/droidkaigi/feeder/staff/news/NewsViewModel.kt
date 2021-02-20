package io.github.droidkaigi.feeder.staff.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.News
import io.github.droidkaigi.feeder.NewsContents
import io.github.droidkaigi.feeder.staff.UnidirectionalViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface NewsViewModel :
    UnidirectionalViewModel<NewsViewModel.Event, NewsViewModel.Effect, NewsViewModel.State> {
    data class State(
        val showProgress: Boolean = false,
        val filters: Filters = Filters(),
        val filteredNewsContents: NewsContents = NewsContents(),
    )

    sealed class Effect {
        data class ErrorMessage(val appError: AppError) : Effect()
    }

    sealed class Event {
        class ChangeFavoriteFilter(val filters: Filters) : Event()
        class ToggleFavorite(val news: News) : Event()
    }

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val LocalNewsViewModel = compositionLocalOf<NewsViewModel>()

@Composable
fun ProvideNewsViewModel(viewModel: NewsViewModel, block: @Composable () -> Unit) {
    Providers(LocalNewsViewModel provides viewModel, content = block)
}

@Composable
fun newsViewModel() = LocalNewsViewModel.current
