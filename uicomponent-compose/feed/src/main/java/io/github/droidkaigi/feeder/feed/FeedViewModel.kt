package io.github.droidkaigi.feeder.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.core.UnidirectionalViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FeedViewModel :
    UnidirectionalViewModel<FeedViewModel.Event, FeedViewModel.Effect, FeedViewModel.State> {
    data class State(
        val showProgress: Boolean = false,
        val filters: Filters = Filters(),
        val filteredFeedContents: FeedContents = FeedContents(),
    )

    sealed class Effect {
        data class ErrorMessage(val appError: AppError) : Effect()
    }

    sealed class Event {
        class ChangeFavoriteFilter(val filters: Filters) : Event()
        class ToggleFavorite(val feedItem: FeedItem) : Event()
        object ReloadContent : Event()
    }

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val LocalFeedViewModel = compositionLocalOf<FeedViewModel> {
    error("not LocalFeedViewModel provided")
}

fun feedViewModelProviderValue(viewModel: FeedViewModel) =
    LocalFeedViewModel provides viewModel

@Composable
fun feedViewModel() = LocalFeedViewModel.current
