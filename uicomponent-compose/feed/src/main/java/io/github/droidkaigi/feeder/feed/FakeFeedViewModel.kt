package io.github.droidkaigi.feeder.feed

import app.cash.exhaustive.Exhaustive
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.fakeFeedContents
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun fakeFeedViewModel(errorFetchData: Boolean = false): FakeFeedViewModel {
    return FakeFeedViewModel(errorFetchData)
}

class FakeFeedViewModel(val errorFetchData: Boolean) : FeedViewModel {

    private val effectChannel = Channel<FeedViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<FeedViewModel.Effect> = effectChannel.receiveAsFlow()

    private val coroutineScope = CoroutineScope(object : CoroutineDispatcher() {
        // for preview
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            block.run()
        }
    })
    private val mutableFeedContents = MutableStateFlow(
        fakeFeedContents()
    )
    private val errorFeedContents = flow<FeedContents> {
        throw AppError.ApiException.ServerException(null)
    }
        .catch { error ->
            effectChannel.send(FeedViewModel.Effect.ErrorMessage(error as AppError))
        }
        .stateIn(coroutineScope, SharingStarted.Lazily, fakeFeedContents())

    private val mFeedContents: StateFlow<FeedContents> = if (errorFetchData) {
        errorFeedContents
    } else {
        mutableFeedContents
    }

    private val filters: MutableStateFlow<Filters> = MutableStateFlow(Filters())
    private val selectedTab: MutableStateFlow<FeedTabs> = MutableStateFlow(FeedTabs.Home)

    override val state: StateFlow<FeedViewModel.State> =
        combine(mFeedContents, filters) { feedContents, filters ->
            val filteredFeed = feedContents.filtered(filters)
            FeedViewModel.State(
                filters = filters,
                filteredFeedContents = filteredFeed
            )
        }
            .stateIn(coroutineScope, SharingStarted.Eagerly, FeedViewModel.State())

    override fun event(event: FeedViewModel.Event) {
        coroutineScope.launch {
            @Exhaustive
            when (event) {
                is FeedViewModel.Event.ChangeFavoriteFilter -> {
                    filters.value = event.filters
                }
                is FeedViewModel.Event.ToggleFavorite -> {
                    val value = mFeedContents.value
                    val newFavorites = if (!value.favorites.contains(event.feedItem.id)) {
                        value.favorites + event.feedItem.id
                    } else {
                        value.favorites - event.feedItem.id
                    }
                    mutableFeedContents.value = value.copy(
                        favorites = newFavorites
                    )
                }
                is FeedViewModel.Event.ToggleTab -> {
                    selectedTab.value = event.selectedTab
                }
            }
        }
    }
}
