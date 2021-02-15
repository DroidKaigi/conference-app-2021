package io.github.droidkaigi.confnews2021.staff.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.AmbientWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import dev.chrisbanes.accompanist.insets.toPaddingValues
import io.github.droidkaigi.confnews2021.Filters
import io.github.droidkaigi.confnews2021.News
import io.github.droidkaigi.confnews2021.NewsContents
import io.github.droidkaigi.confnews2021.staff.getReadableMessage
import io.github.droidkaigi.confnews2021.staff.theme.Conferenceapp2021newsTheme
import io.github.droidkaigi.confnews2021.staff.use
import io.github.droidkaigi.confnews2021.staff.util.collectInLaunchedEffect
import io.github.droidkaigi.confnews2021.uicomponent.news.R
import kotlin.reflect.KClass

sealed class NewsTabs(val name: String, val routePath: String) {
    object Home : NewsTabs("Home", "home")
    sealed class FilteredNews(val newsClass: KClass<out News>, name: String, routePath: String) :
        NewsTabs(name, routePath) {
        object Blog : FilteredNews(News.Blog::class, "Blog", "blog")
        object Video : FilteredNews(News.Video::class, "Video", "video")
        object Podcast : FilteredNews(News.Podcast::class, "Podcast", "podcast")
    }

    companion object {
        fun values() = listOf(Home, FilteredNews.Blog, FilteredNews.Video, FilteredNews.Podcast)

        fun ofRoutePath(routePath: String) = values().first { it.routePath == routePath }
    }
}

/**
 * stateful
 */
@Composable
fun NewsScreen(
    initialSelectedTab: NewsTabs,
    onNavigationIconClick: () -> Unit,
    onDetailClick: (News) -> Unit,
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    var selectedTab by remember(initialSelectedTab) {
        mutableStateOf(initialSelectedTab)
    }

    val (
        state,
        effectFlow,
        dispatch,
    ) = use(newsViewModel())

    val context = LocalContext.current
    effectFlow.collectInLaunchedEffect { effect ->
        when (effect) {
            is NewsViewModel.Effect.ErrorMessage -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    effect.appError.getReadableMessage(context)
                )
            }
        }
    }

    NewsScreen(
        selectedTab = selectedTab,
        scaffoldState = scaffoldState,
        newsContents = state.filteredNewsContents,
        filters = state.filters,
        onSelectTab = { tab: NewsTabs ->
            selectedTab = tab
        },
        onNavigationIconClick = onNavigationIconClick,
        onFavoriteChange = {
            dispatch(NewsViewModel.Event.ToggleFavorite(news = it))
        },
        onFavoriteFilterChanged = {
            dispatch(
                NewsViewModel.Event.ChangeFavoriteFilter(
                    filters = state.filters.copy(filterFavorite = it)
                )
            )
        },
        onClickNews = onDetailClick
    )
}

/**
 * stateless
 */
@Composable
private fun NewsScreen(
    selectedTab: NewsTabs,
    scaffoldState: BackdropScaffoldState,
    newsContents: NewsContents,
    filters: Filters,
    onSelectTab: (NewsTabs) -> Unit,
    onNavigationIconClick: () -> Unit,
    onFavoriteChange: (News) -> Unit,
    onFavoriteFilterChanged: (filtered: Boolean) -> Unit,
    onClickNews: (News) -> Unit,
) {
    Column {
        val density = AmbientDensity.current
        BackdropScaffold(
            backLayerBackgroundColor = MaterialTheme.colors.primary,
            scaffoldState = scaffoldState,
            backLayerContent = {
                BackLayerContent(filters, onFavoriteFilterChanged)
            },
            frontLayerShape = CutCornerShape(topStart = 32.dp),
            peekHeight = 104.dp + (AmbientWindowInsets.current.systemBars.top / density.density).dp,
            appBar = {
                AppBar(onNavigationIconClick, selectedTab, onSelectTab)
            },
            frontLayerContent = {
                val isHome = selectedTab is NewsTabs.Home
                NewsList(
                    newsContents = if (selectedTab is NewsTabs.FilteredNews) {
                        newsContents.filterNewsType(selectedTab.newsClass)
                    } else {
                        newsContents
                    },
                    isHome = isHome,
                    onClickNews = onClickNews,
                    onFavoriteChange = onFavoriteChange
                )
            }
        )
    }
}

@Composable
private fun AppBar(
    onNavigationIconClick: () -> Unit,
    selectedTab: NewsTabs,
    onSelectTab: (NewsTabs) -> Unit,
) {
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = { Text("DroidKaigi") },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(vectorResource(R.drawable.ic_baseline_menu_24), "menu")
            }
        }
    )
    TabRow(
        selectedTabIndex = 0,
        indicator = {
        },
        divider = {}
    ) {
        NewsTabs.values().forEach { tab ->
            Tab(
                selected = tab == selectedTab,
                text = {
                    Text(
                        modifier = if (selectedTab == tab) {
                            Modifier
                                .background(
                                    color = MaterialTheme.colors.secondary,
                                    shape = CutCornerShape(
                                        topStart = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                )
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        } else {
                            Modifier
                        },
                        text = tab.name
                    )
                },
                onClick = { onSelectTab(tab) }
            )
        }
    }
}

@Composable
private fun NewsList(
    newsContents: NewsContents,
    isHome: Boolean,
    onClickNews: (News) -> Unit,
    onFavoriteChange: (News) -> Unit,
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxHeight()
    ) {
        LazyColumn(
            contentPadding = AmbientWindowInsets.current.systemBars.toPaddingValues(top = false)
        ) {
            if (newsContents.size > 0) {
                items(newsContents.contents.size * 2) { index ->
                    if (index % 2 == 0) {
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    } else {
                        val (item, favorited) = newsContents.contents[index / 2]
                        NewsItem(
                            news = item,
                            favorited = favorited,
                            onClick = onClickNews,
                            showMediaLabel = isHome,
                            onFavoriteChange = onFavoriteChange
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsScreen() {
    Conferenceapp2021newsTheme(false) {
        ProvideNewsViewModel(viewModel = fakeNewsViewModel()) {
            NewsScreen(
                initialSelectedTab = NewsTabs.Home,
                onNavigationIconClick = {
                }
            ) { news: News ->
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsScreenWithStartBlog() {
    Conferenceapp2021newsTheme(false) {
        ProvideNewsViewModel(viewModel = fakeNewsViewModel()) {
            NewsScreen(
                initialSelectedTab = NewsTabs.FilteredNews.Blog,
                onNavigationIconClick = {
                }
            ) { news: News ->
            }
        }
    }
}
