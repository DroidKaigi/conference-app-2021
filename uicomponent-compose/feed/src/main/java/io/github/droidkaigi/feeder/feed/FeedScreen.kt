package io.github.droidkaigi.feeder.feed

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import io.github.droidkaigi.feeder.FeedContents
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.Theme
import io.github.droidkaigi.feeder.core.R as CoreR
import io.github.droidkaigi.feeder.core.TabIndicator
import io.github.droidkaigi.feeder.core.theme.AppThemeWithBackground
import io.github.droidkaigi.feeder.core.theme.greenDroid
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

    companion object {
        fun values() = listOf(Home, FilteredFeed.Blog, FilteredFeed.Video, FilteredFeed.Podcast)
        fun ofRoutePath(routePath: String) = values().find { it.routePath == routePath } ?: Home
    }
}

/**
 * stateful
 */
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun FeedScreen(
    feedScreenState: FeedScreenState = rememberFeedScreenState(),
    pagerState: PagerState,
    onSelectedTab: (FeedTab) -> Unit,
    onNavigationIconClick: () -> Unit,
    onDroidKaigi2021ArticleClick: () -> Unit,
    isDroidKaigiEnd: Boolean,
    onDetailClick: (FeedItem) -> Unit,
) {

    feedScreenState.effect.collectInLaunchedEffect { effect ->
        when (effect) {
            is FeedViewModel.Effect.ErrorMessage -> {
                feedScreenState.onErrorMessage(effect)
            }
        }
    }

    val uiState = feedScreenState.uiState
    FeedScreen(
        scaffoldState = feedScreenState.scaffoldState,
        pagerState = pagerState,
        tabLazyListStates = feedScreenState.tabLazyListStates,
        feedContents = uiState.filteredFeedContents,
        fmPlayerState = feedScreenState.fmPlayerUiState,
        filters = uiState.filters,
        onSelectTab = onSelectedTab,
        onNavigationIconClick = onNavigationIconClick,
        onFavoriteChange = feedScreenState::onFavoriteChange,
        onFavoriteFilterChanged = {
            feedScreenState.onFavoriteFilterChange(uiState.filters, it)
        },
        onClickFeed = onDetailClick,
        onClickPlayPodcastButton = feedScreenState::onPotcastPlayButtonClick,
        onClickDroidKaigi2021Article = onDroidKaigi2021ArticleClick,
        isDroidKaigiEnd = isDroidKaigiEnd
    )
}

/**
 * stateless
 */
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
private fun FeedScreen(
    scaffoldState: BackdropScaffoldState,
    pagerState: PagerState,
    feedContents: FeedContents,
    tabLazyListStates: Map<FeedTab, LazyListState>,
    fmPlayerState: FmPlayerViewModel.State?,
    filters: Filters,
    onSelectTab: (FeedTab) -> Unit,
    onNavigationIconClick: () -> Unit,
    onFavoriteChange: (FeedItem) -> Unit,
    onFavoriteFilterChanged: (filtered: Boolean) -> Unit,
    onClickFeed: (FeedItem) -> Unit,
    onClickPlayPodcastButton: (FeedItem.Podcast) -> Unit,
    onClickDroidKaigi2021Article: () -> Unit,
    isDroidKaigiEnd: Boolean,
) {
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
            AppBar(onNavigationIconClick, pagerState, tabLazyListStates, onSelectTab)
        },
        frontLayerContent = {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                count = FeedTab.values().size,
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
                    listState = tabLazyListStates.getValue(selectedTab),
                    onClickPlayPodcastButton = onClickPlayPodcastButton,
                    onClickArticleItem = onClickDroidKaigi2021Article,
                    isFilterState = filters.filterFavorite,
                    isDroidKaigiEnd = isDroidKaigiEnd
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

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun AppBar(
    onNavigationIconClick: () -> Unit,
    pagerState: PagerState,
    tabLazyListStates: Map<FeedTab, LazyListState>,
    onSelectTab: (FeedTab) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = { Image(painterResource(CoreR.drawable.toolbar_droidkaigi_logo), "DroidKaigi") },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(painterResource(CoreR.drawable.ic_baseline_menu_24), "menu")
            }
        }
    )
    ScrollableTabRow(
        selectedTabIndex = 0,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
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
                        tabLazyListStates.getValue(tab).animateScrollToItem(index = 0)
                    }
                },
                // For tabs to draw in front of indicators
                modifier = Modifier.zIndex(1f)
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
    onClickArticleItem: () -> Unit,
    listState: LazyListState,
    isFilterState: Boolean,
    isDroidKaigiEnd: Boolean,
) {
    val isHome = feedTab is FeedTab.Home
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        val isListFinished by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex + listState.layoutInfo
                    .visibleItemsInfo.size == listState.layoutInfo.totalItemsCount
            }
        }
        LazyColumn(
            contentPadding = rememberInsetsPaddingValues(
                insets = LocalWindowInsets.current.systemBars,
                applyStart = false,
                applyTop = false,
                applyEnd = false
            ),
            state = listState
        ) {
            itemsIndexed(
                items = feedContents.contents,
                key = { _, item -> item.first.id.value }
            ) { index, (feedItem, favorited) ->
                if (isHome && index == 0) {
                    if (isFilterState) {
                        FilterItemCountRow(feedContents.size.toString())
                    } else if (!isDroidKaigiEnd) {
                        DroidKaigi2021ArticleItem(
                            onClick = onClickArticleItem,
                            shouldPadding = isFilterState,
                        )
                    }
                }
                if (isDroidKaigiEnd && isHome && index == 0) {
                    FirstFeedItem(
                        feedItem = feedItem,
                        favorited = favorited,
                        onClick = onClickFeed,
                        showMediaLabel = isHome,
                        onFavoriteChange = onFavoriteChange,
                        shouldPadding = isFilterState,
                    )
                } else {
                    FeedItemRow(
                        item = feedItem,
                        favorited = favorited,
                        onClickFeed = onClickFeed,
                        showMediaLabel = isHome,
                        onFavoriteChange = onFavoriteChange,
                        showDivider = index != 0,
                        isPlayingPodcast = (feedItem as? FeedItem.Podcast)
                            ?.podcastLink == fmPlayerState?.url &&
                            fmPlayerState?.isPlaying() == true,
                        onClickPlayPodcastButton = onClickPlayPodcastButton,
                    )
                }
                if (index == feedContents.lastIndex) {
                    val robotAnimValue by animateFloatAsState(
                        targetValue = if (isListFinished) 0f else 10f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioHighBouncy,
                            stiffness = Spring.StiffnessMedium,
                        )
                    )
                    RobotItem(
                        robotText = "Finished!",
                        robotIcon = painterResource(id = CoreR.drawable.ic_android_green_24dp),
                        robotIconColor = greenDroid,
                        targetValue = robotAnimValue.dp,
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

@Composable
fun FilterItemCountRow(count: String) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val text = createRef()
        Text(
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, 44.dp)
                }
                .padding(vertical = 8.dp, horizontal = 0.dp),
            text = "該当アイテム:$count"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedScreen() {
    AppThemeWithBackground {
        CompositionLocalProvider(
            provideFeedViewModelFactory { fakeFeedViewModel() },
            provideFmPlayerViewModelFactory { fakeFmPlayerViewModel() }
        ) {
            FeedScreen(
                pagerState = rememberPagerState(
                    initialPage = FeedTab.values().indexOf(FeedTab.Home)
                ),
                onSelectedTab = {},
                onNavigationIconClick = {},
                onDroidKaigi2021ArticleClick = {},
                isDroidKaigiEnd = false,
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
        CompositionLocalProvider(
            provideFeedViewModelFactory { fakeFeedViewModel() },
            provideFmPlayerViewModelFactory { fakeFmPlayerViewModel() }
        ) {
            FeedScreen(
                pagerState = rememberPagerState(
                    initialPage = FeedTab.values().indexOf(FeedTab.Home)
                ),
                onSelectedTab = {},
                onNavigationIconClick = {},
                onDroidKaigi2021ArticleClick = {},
                isDroidKaigiEnd = false,
            ) { feedItem: FeedItem ->
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedScreenWhenDroidKaigiEnd() {
    AppThemeWithBackground {
        CompositionLocalProvider(
            provideFeedViewModelFactory { fakeFeedViewModel() },
            provideFmPlayerViewModelFactory { fakeFmPlayerViewModel() }
        ) {
            FeedScreen(
                pagerState = rememberPagerState(
                    initialPage = FeedTab.values().indexOf(FeedTab.Home)
                ),
                onSelectedTab = {},
                onNavigationIconClick = {},
                onDroidKaigi2021ArticleClick = {},
                isDroidKaigiEnd = true,
            ) { feedItem: FeedItem ->
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDarkFeedScreenWhenDroidKaigiEnd() {
    AppThemeWithBackground(
        theme = Theme.DARK
    ) {
        CompositionLocalProvider(
            provideFeedViewModelFactory { fakeFeedViewModel() },
            provideFmPlayerViewModelFactory { fakeFmPlayerViewModel() }
        ) {
            FeedScreen(
                pagerState = rememberPagerState(
                    initialPage = FeedTab.values().indexOf(FeedTab.Home)
                ),
                onSelectedTab = {},
                onNavigationIconClick = {},
                onDroidKaigi2021ArticleClick = {},
                isDroidKaigiEnd = true,
            ) { feedItem: FeedItem ->
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedScreenWithStartBlog() {
    AppThemeWithBackground {
        CompositionLocalProvider(
            provideFeedViewModelFactory { fakeFeedViewModel() },
            provideFmPlayerViewModelFactory { fakeFmPlayerViewModel() }
        ) {
            FeedScreen(
                pagerState = rememberPagerState(
                    initialPage = FeedTab.values().indexOf(FeedTab.FilteredFeed.Blog)
                ),
                onSelectedTab = {},
                onNavigationIconClick = {},
                onDroidKaigi2021ArticleClick = {},
                isDroidKaigiEnd = false,
            ) { feedItem: FeedItem ->
            }
        }
    }
}
