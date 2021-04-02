package io.github.droidkaigi.feeder.feed

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.insets.toPaddingValues
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.Theme
import io.github.droidkaigi.feeder.core.ScrollableTabRow
import io.github.droidkaigi.feeder.core.TabIndicator
import io.github.droidkaigi.feeder.core.TabPosition
import io.github.droidkaigi.feeder.core.getReadableMessage
import io.github.droidkaigi.feeder.core.theme.AppThemeWithBackground
import io.github.droidkaigi.feeder.core.theme.greenDroid
import io.github.droidkaigi.feeder.core.use
import io.github.droidkaigi.feeder.core.util.collectInLaunchedEffect
import kotlin.reflect.KClass
import kotlinx.coroutines.launch

sealed class FeedTab(val name: String, val routePath: String) {
    object Home : FeedTab("Home", "home")
    sealed class FilteredFeed(
        val feedItemClass: KClass<out FeedItem>,
        name: String,
        routePath: String,
    ) :
        FeedTab(name, routePath) {
        object Blog : FilteredFeed(FeedItem.Blog::class, "Blog", "blog")
        object Video : FilteredFeed(FeedItem.Video::class, "Video", "video")
        object Podcast : FilteredFeed(FeedItem.Podcast::class, "Podcast", "podcast")
    }

    val listState = LazyListState()

    companion object {
        fun values() = listOf(Home, FilteredFeed.Blog, FilteredFeed.Video, FilteredFeed.Podcast)
        fun ofRoutePath(routePath: String) = values().find { it.routePath == routePath } ?: Home
    }
}

/**
 * stateful
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeedScreen(
    selectedTab: FeedTab,
    onSelectedTab: (FeedTab) -> Unit,
    onNavigationIconClick: () -> Unit,
    onDetailClick: (FeedItem) -> Unit,
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val pagerState = rememberPagerState(
        pageCount = FeedTab.values().size,
        initialPage = FeedTab.values().indexOf(selectedTab)
    )

    val (
        state,
        effectFlow,
        dispatch,
    ) = use(feedViewModel())

    val (
        fmPlayerState,
        fmPlayerEffectFlow,
        fmPlayerDispatch,
    ) = use(fmPlayerViewModel())

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
        scaffoldState = scaffoldState,
        pagerState = pagerState,
        feedContents = state.filteredFeedContents,
        fmPlayerState = fmPlayerState,
        filters = state.filters,
        onSelectTab = onSelectedTab,
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
        onClickPlayPodcastButton = {
            fmPlayerDispatch(FmPlayerViewModel.Event.ChangePlayerState(it.podcastLink))
        },
    )
}

/**
 * stateless
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun FeedScreen(
    scaffoldState: BackdropScaffoldState,
    pagerState: PagerState,
    feedContents: FeedContents,
    fmPlayerState: FmPlayerViewModel.State?,
    filters: Filters,
    onSelectTab: (FeedTab) -> Unit,
    onNavigationIconClick: () -> Unit,
    onFavoriteChange: (FeedItem) -> Unit,
    onFavoriteFilterChanged: (filtered: Boolean) -> Unit,
    onClickFeed: (FeedItem) -> Unit,
    onClickPlayPodcastButton: (FeedItem.Podcast) -> Unit,
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
                AppBar(onNavigationIconClick, pagerState, onSelectTab)
            },
            frontLayerContent = {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    val selectedTab = FeedTab.values()[page]
                    FeedList(
                        feedContents = if (selectedTab is FeedTab.FilteredFeed) {
                            feedContents.filterFeedType(selectedTab.feedItemClass)
                        } else {
                            feedContents
                        },
                        fmPlayerState = fmPlayerState,
                        feedTab = selectedTab,
                        onClickFeed = onClickFeed,
                        onFavoriteChange = onFavoriteChange,
                        listState = selectedTab.listState,
                        onClickPlayPodcastButton = onClickPlayPodcastButton,
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = it,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun AppBar(
    onNavigationIconClick: () -> Unit,
    pagerState: PagerState,
    onSelectTab: (FeedTab) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
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
    ScrollableTabRow(
        selectedTabIndex = 0,
        edgePadding = 0.dp,
        foregroundIndicator = {},
        backgroundIndicator = { tabPositions ->
            TabIndicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        divider = {}
    ) {
        FeedTab.values().forEachIndexed { index, tab ->
            Tab(
                selected = index == pagerState.currentPage,
                text = {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        text = tab.name,
                    )
                },
                onClick = {
                    onSelectTab(tab)
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                        tab.listState.animateScrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

@Composable
private fun FeedList(
    feedContents: FeedContents,
    fmPlayerState: FmPlayerViewModel.State?,
    feedTab: FeedTab,
    onClickFeed: (FeedItem) -> Unit,
    onFavoriteChange: (FeedItem) -> Unit,
    onClickPlayPodcastButton: (FeedItem.Podcast) -> Unit,
    listState: LazyListState,
) {
    val isHome = feedTab is FeedTab.Home
    val isListFinished by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex + listState.layoutInfo
                .visibleItemsInfo.size == listState.layoutInfo.totalItemsCount
        }
    }
    val robotAnimValue by animateFloatAsState(
        targetValue = if (isListFinished) 0f else 10f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMedium,
        )
    )
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = LocalWindowInsets.current.systemBars
                .toPaddingValues(top = false, start = false, end = false),
            state = feedTab.listState
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
                        isPlayingPodcast = (content.first as? FeedItem.Podcast)
                            ?.podcastLink == fmPlayerState?.url &&
                            fmPlayerState?.isPlaying() == true,
                        onClickPlayPodcastButton = onClickPlayPodcastButton
                    )
                }
            }
            if (listState.firstVisibleItemIndex != 0) {
                item {
                    RobotItem(
                        robotText = "Finished!",
                        robotIcon = painterResource(id = R.drawable.ic_android_green_24dp),
                        robotIconColor = greenDroid,
                        targetValue = robotAnimValue.dp

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
    onClickPlayPodcastButton: (FeedItem.Podcast) -> Unit,
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

@Composable
fun RobotItem(
    robotText: String,
    robotIcon: Painter,
    robotIconColor: Color,
    targetValue: Dp,
) {
    Divider()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = targetValue)
            .semantics(mergeDescendants = true) { }
    ) {
        val (text, icon) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    bottom.linkTo(icon.top)
                    start.linkTo(parent.start, 24.dp)
                }
                .padding(vertical = 0.dp, horizontal = 8.dp),
            text = robotText,
            color = Color.Gray
        )
        Icon(
            modifier = Modifier
                .constrainAs(icon) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(text.start)
                    end.linkTo(text.end)
                }
                .padding(horizontal = 8.dp),
            painter = robotIcon,
            contentDescription = "",
            tint = robotIconColor
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
fun Modifier.pagerTabIndicatorOffset(
    pagerState: PagerState,
    tabPositions: List<TabPosition>,
): Modifier = composed {
    val targetIndicatorOffset: Dp
    val indicatorWidth: Dp

    val currentTab = tabPositions[pagerState.currentPage]
    val nextTab = tabPositions.getOrNull(pagerState.currentPage + 1)
    if (nextTab != null) {
        // If we have a next tab, lerp between the size and offset
        targetIndicatorOffset = lerp(currentTab.left, nextTab.left, pagerState.currentPageOffset)
        indicatorWidth = lerp(currentTab.width, nextTab.width, pagerState.currentPageOffset)
    } else {
        // Otherwise we just use the current tab/page
        targetIndicatorOffset = currentTab.left
        indicatorWidth = currentTab.width
    }

    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = targetIndicatorOffset)
        .width(indicatorWidth)
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedScreen() {
    AppThemeWithBackground {
        CompositionLocalProvider(feedViewModelProviderValue(fakeFeedViewModel())) {
            FeedScreen(
                selectedTab = FeedTab.Home,
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
fun PreviewDarkFeedScreen() {
    AppThemeWithBackground(
        theme = Theme.DARK
    ) {
        CompositionLocalProvider(feedViewModelProviderValue(fakeFeedViewModel())) {
            FeedScreen(
                selectedTab = FeedTab.Home,
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
        CompositionLocalProvider(feedViewModelProviderValue(fakeFeedViewModel())) {
            FeedScreen(
                selectedTab = FeedTab.FilteredFeed.Blog,
                onSelectedTab = {},
                onNavigationIconClick = {
                }
            ) { feedItem: FeedItem ->
            }
        }
    }
}
