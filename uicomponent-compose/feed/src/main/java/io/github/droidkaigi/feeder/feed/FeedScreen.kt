package io.github.droidkaigi.feeder.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import dev.chrisbanes.accompanist.insets.toPaddingValues
import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.PlayingPodcastState
import io.github.droidkaigi.feeder.core.ScrollableTabRow
import io.github.droidkaigi.feeder.core.TabIndicator
import io.github.droidkaigi.feeder.core.TabRowDefaults.tabIndicatorOffset
import io.github.droidkaigi.feeder.core.animation.FadeThrough
import io.github.droidkaigi.feeder.core.getReadableMessage
import io.github.droidkaigi.feeder.core.theme.AppThemeWithBackground
import io.github.droidkaigi.feeder.core.use
import io.github.droidkaigi.feeder.core.util.collectInLaunchedEffect
import kotlin.reflect.KClass
import kotlinx.coroutines.launch
import kotlin.math.abs

sealed class FeedTabs(val name: String, val routePath: String) {
    object Home : FeedTabs("Home", "home")
    sealed class FilteredFeed(
        val feedItemClass: KClass<out FeedItem>,
        name: String,
        routePath: String,
    ) :
        FeedTabs(name, routePath) {
        object Blog : FilteredFeed(FeedItem.Blog::class, "Blog", "blog")
        object Video : FilteredFeed(FeedItem.Video::class, "Video", "video")
        object Podcast : FilteredFeed(FeedItem.Podcast::class, "Podcast", "podcast")
    }

    companion object {

        fun values() = listOf(Home, FilteredFeed.Blog, FilteredFeed.Video, FilteredFeed.Podcast)

        fun rightTab(selectedTab: FeedTabs): FeedTabs {
            val currentPosition = values().indexOf(selectedTab)
            return values().getOrElse(currentPosition + 1) { selectedTab }
        }

        fun leftTab(selectedTab: FeedTabs): FeedTabs {
            val currentPosition = values().indexOf(selectedTab)
            return values().getOrElse(currentPosition - 1) { selectedTab }
        }

        fun ofRoutePath(routePath: String) = values().find { it.routePath == routePath } ?: Home
    }
}

/**
 * stateful
 */
@Composable
fun FeedScreen(
    selectedTab: FeedTabs,
    onSelectedTab: (FeedTabs) -> Unit,
    onNavigationIconClick: () -> Unit,
    onDetailClick: (FeedItem) -> Unit,
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val listState = rememberLazyListState()
    val draggableState = rememberDraggableState(onDelta = { })
    val coroutineScope = rememberCoroutineScope()

    val (
        state,
        effectFlow,
        dispatch,
    ) = use(feedViewModel())

    val context = LocalContext.current
    effectFlow.collectInLaunchedEffect { effect ->
        when (effect) {
            is FeedViewModel.Effect.ErrorMessage -> {
                when (
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = effect.appError.getReadableMessage(context),
                        actionLabel = "Reload",
                    )
                ) {
                    SnackbarResult.ActionPerformed -> {
                        dispatch(FeedViewModel.Event.ReloadContent)
                    }
                    SnackbarResult.Dismissed -> {
                    }
                }
            }
        }
    }

    FeedScreen(
        selectedTab = selectedTab,
        scaffoldState = scaffoldState,
        feedContents = state.filteredFeedContents,
        playingPodcastState = state.playingPodcastState,
        filters = state.filters,
        onSelectTab = {
            onSelectedTab(it)
            coroutineScope.launch {
                listState.animateScrollToItem(index = 0)
            }
        },
        onNavigationIconClick = onNavigationIconClick,
        onFavoriteChange = {
            dispatch(FeedViewModel.Event.ToggleFavorite(feedItem = it))
        },
        onFavoriteFilterChanged = {
            dispatch(
                FeedViewModel.Event.ChangeFavoriteFilter(
                    filters = state.filters.copy(filterFavorite = it)
                )
            )
        },
        onClickFeed = onDetailClick,
        listState = listState,
        onClickPlayPodcastButton = {
            dispatch(FeedViewModel.Event.ChangePlayingPodcastState(it))
        },
        onDragStopped = onDragStopped@{ velocity ->
            val threshold = 500
            when {
                threshold > abs(velocity) -> return@onDragStopped
                0 > velocity -> onSelectedTab(FeedTabs.rightTab(selectedTab))
                0 < velocity -> onSelectedTab(FeedTabs.leftTab(selectedTab))
            }
        },
        draggableState = draggableState
    )
}

/**
 * stateless
 */
@Composable
private fun FeedScreen(
    selectedTab: FeedTabs,
    scaffoldState: BackdropScaffoldState,
    feedContents: FeedContents,
    playingPodcastState: PlayingPodcastState?,
    filters: Filters,
    onSelectTab: (FeedTabs) -> Unit,
    onNavigationIconClick: () -> Unit,
    onFavoriteChange: (FeedItem) -> Unit,
    onFavoriteFilterChanged: (filtered: Boolean) -> Unit,
    onClickFeed: (FeedItem) -> Unit,
    onClickPlayPodcastButton: (FeedItem) -> Unit,
    listState: LazyListState,
    onDragStopped: (Float) -> Unit,
    draggableState: DraggableState,
) {
    Column {
        val density = LocalDensity.current
        BackdropScaffold(
            backLayerBackgroundColor = MaterialTheme.colors.primarySurface,
            scaffoldState = scaffoldState,
            backLayerContent = {
                BackLayerContent(filters, onFavoriteFilterChanged)
            },
            frontLayerShape = MaterialTheme.shapes.large,
            peekHeight = 104.dp + (LocalWindowInsets.current.systemBars.top / density.density).dp,
            appBar = {
                AppBar(onNavigationIconClick, selectedTab, onSelectTab)
            },
            frontLayerContent = {
                FadeThrough(targetState = selectedTab) { selectedTab ->
                    val isHome = selectedTab is FeedTabs.Home
                    FeedList(
                        feedContents = if (selectedTab is FeedTabs.FilteredFeed) {
                            feedContents.filterFeedType(selectedTab.feedItemClass)
                        } else {
                            feedContents
                        },
                        playingPodcastState = playingPodcastState,
                        isHome = isHome,
                        onClickFeed = onClickFeed,
                        onFavoriteChange = onFavoriteChange,
                        listState = listState,
                        onClickPlayPodcastButton = onClickPlayPodcastButton,
                        onDragStopped = onDragStopped,
                        draggableState = draggableState
                    )
                }
            }
        )
    }
}

@Composable
private fun AppBar(
    onNavigationIconClick: () -> Unit,
    selectedTab: FeedTabs,
    onSelectTab: (FeedTabs) -> Unit,
) {
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = { Image(painterResource(R.drawable.toolbar_droidkaigi_logo), "DroidKaigi") },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(painterResource(R.drawable.ic_baseline_menu_24), "menu")
            }
        }
    )
    val selectedTabIndex = FeedTabs.values().indexOf(selectedTab)
    ScrollableTabRow(
        selectedTabIndex = 0,
        edgePadding = 0.dp,
        foregroundIndicator = {},
        backgroundIndicator = { tabPositions ->
            TabIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        },
        divider = {}
    ) {
        FeedTabs.values().forEach { tab ->
            Tab(
                selected = tab == selectedTab,
                text = {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        text = tab.name,
                    )
                },
                onClick = { onSelectTab(tab) }
            )
        }
    }
}

@Composable
private fun FeedList(
    feedContents: FeedContents,
    playingPodcastState: PlayingPodcastState?,
    isHome: Boolean,
    onClickFeed: (FeedItem) -> Unit,
    onFavoriteChange: (FeedItem) -> Unit,
    onClickPlayPodcastButton: (FeedItem) -> Unit,
    listState: LazyListState,
    onDragStopped: (Float) -> Unit,
    draggableState: DraggableState,
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxHeight()
            .draggable(
                state = draggableState,
                orientation = Orientation.Horizontal,
                onDragStopped = { velocity -> onDragStopped(velocity) }
            )
    ) {
        LazyColumn(
            contentPadding = LocalWindowInsets.current.systemBars
                .toPaddingValues(top = false, start = false, end = false),
            state = listState
        ) {
            itemsIndexed(feedContents.contents) { index, content ->
                if (isHome && index == 0) {
                    FirstFeedItem(
                        feedItem = content.first,
                        favorited = content.second,
                        onClick = onClickFeed,
                        showMediaLabel = isHome,
                        onFavoriteChange = onFavoriteChange
                    )
                } else {
                    FeedItemRow(
                        item = content.first,
                        favorited = content.second,
                        onClickFeed = onClickFeed,
                        showMediaLabel = isHome,
                        onFavoriteChange = onFavoriteChange,
                        showDivider = index != 0,
                        isPlayingPodcast =
                        content.first.id == playingPodcastState?.id &&
                            playingPodcastState.isPlaying,
                        onClickPlayPodcastButton = onClickPlayPodcastButton
                    )
                }
            }
        }
    }
}

@Composable
fun FeedItemRow(
    item: FeedItem,
    favorited: Boolean,
    isPlayingPodcast: Boolean = false,
    onClickFeed: (FeedItem) -> Unit,
    showMediaLabel: Boolean,
    onFavoriteChange: (FeedItem) -> Unit,
    onClickPlayPodcastButton: (FeedItem) -> Unit,
    showDivider: Boolean,
) {
    if (showDivider) {
        Divider()
    }
    FeedItem(
        feedItem = item,
        favorited = favorited,
        isPlayingPodcast = isPlayingPodcast,
        onClick = onClickFeed,
        showMediaLabel = showMediaLabel,
        onFavoriteChange = onFavoriteChange,
        onClickPlayPodcastButton = onClickPlayPodcastButton
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedScreen() {
    AppThemeWithBackground {
        ProvideFeedViewModel(viewModel = fakeFeedViewModel()) {
            FeedScreen(
                selectedTab = FeedTabs.Home,
                onSelectedTab = {},
                onNavigationIconClick = {
                }
            ) { feedItem: FeedItem ->
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedScreenWithStartBlog() {
    AppThemeWithBackground {
        ProvideFeedViewModel(viewModel = fakeFeedViewModel()) {
            FeedScreen(
                selectedTab = FeedTabs.FilteredFeed.Blog,
                onSelectedTab = {},
                onNavigationIconClick = {
                }
            ) { feedItem: FeedItem ->
            }
        }
    }
}
