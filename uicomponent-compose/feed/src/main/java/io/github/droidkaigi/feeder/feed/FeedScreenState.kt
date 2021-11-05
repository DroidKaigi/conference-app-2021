package io.github.droidkaigi.feeder.feed

import android.content.Context
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.core.dispatcher
import io.github.droidkaigi.feeder.core.getReadableMessage
import io.github.droidkaigi.feeder.core.state
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterialApi::class)
class FeedScreenState(
    private val feedViewModel: FeedViewModel,
    private val fmPlayerViewModel: FmPlayerViewModel,
    val scaffoldState: BackdropScaffoldState,
    val context: Context,
) {
    val uiState: FeedViewModel.State @Composable get() = feedViewModel.state()
    private val dispatcher: (FeedViewModel.Event) -> Unit get() = feedViewModel.dispatcher()
    val effect: Flow<FeedViewModel.Effect> get() = feedViewModel.effect

    val fmPlayerUiState: FmPlayerViewModel.State @Composable get() = fmPlayerViewModel.state()
    private val fmPlayerDispatcher: (FmPlayerViewModel.Event) -> Unit
        get() = fmPlayerViewModel.dispatcher()

    val tabLazyListStates
        @Composable get() = FeedTab.values()
            .map { it to rememberLazyListState() }
            .toMap()


    suspend fun onErrorMessage(effect: FeedViewModel.Effect.ErrorMessage) {
        when (
            scaffoldState.snackbarHostState.showSnackbar(
                message = effect.appError.getReadableMessage(context),
                actionLabel = "Reload",
            )
        ) {
            SnackbarResult.ActionPerformed -> {
                dispatcher(FeedViewModel.Event.ReloadContent)
            }
            SnackbarResult.Dismissed -> {
            }
        }
    }

    fun onFavoriteChange(feedItem: FeedItem) {
        dispatcher(FeedViewModel.Event.ToggleFavorite(feedItem = feedItem))
    }

    fun onFavoriteFilterChange(currentFilters: Filters, isFavoriteFiltered: Boolean) {
        dispatcher(
            FeedViewModel.Event.ChangeFavoriteFilter(
                filters = currentFilters.copy(filterFavorite = isFavoriteFiltered)

            )
        )
    }

    fun onPotcastPlayButtonClick(podcast: FeedItem.Podcast) {
        fmPlayerDispatcher(FmPlayerViewModel.Event.ChangePlayerState(podcast.podcastLink))
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun rememberFeedScreenState(
    feedViewModel: FeedViewModel = feedViewModel(),
    fmPlayerViewModel: FmPlayerViewModel = fmPlayerViewModel(),
    scaffoldState: BackdropScaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed),
    context: Context = LocalContext.current,
): FeedScreenState {
    return remember {
        FeedScreenState(feedViewModel, fmPlayerViewModel, scaffoldState, context)
    }
}
