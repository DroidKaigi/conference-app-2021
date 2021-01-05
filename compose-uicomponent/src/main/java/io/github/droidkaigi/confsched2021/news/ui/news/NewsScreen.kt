package io.github.droidkaigi.confsched2021.news.ui.news

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2021.news.Filters
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.NewsContents
import io.github.droidkaigi.confsched2021.news.ui.getReadableMessage
import io.github.droidkaigi.confsched2021.news.ui.theme.Conferenceapp2021newsTheme
import io.github.droidkaigi.confsched2021.news.ui.use
import io.github.droidkaigi.confsched2021.news.ui.util.collectInLaunchedEffect
import io.github.droidkaigi.confsched2021.news.uicomponent.R
import kotlin.reflect.KClass

sealed class NewsTabs(val name: String) {
    object Home : NewsTabs("Home")
    sealed class FilteredNews(val newsClass: KClass<out News>, name: String) : NewsTabs(name) {
        object Blog : FilteredNews(News.Blog::class, "Blog")
        object Video : FilteredNews(News.Video::class, "Video")
        object Podcast : FilteredNews(News.Podcast::class, "Podcast")
    }

    companion object {
        fun values() = listOf(Home, FilteredNews.Blog, FilteredNews.Video, FilteredNews.Podcast)
    }
}

/**
 * stateful
 */
@Composable
fun NewsScreen(
    onNavigationIconClick: () -> Unit,
    onDetailClick: (News) -> Unit,
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    var selectedTab by remember<MutableState<NewsTabs>> { mutableStateOf(NewsTabs.Home) }

    val (
        state,
        effectFlow,
        dispatch,
    ) = use(newsViewModel())

    val context = AmbientContext.current
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
        BackdropScaffold(
            backLayerBackgroundColor = MaterialTheme.colors.primary,
            scaffoldState = scaffoldState,
            backLayerContent = {
                BackLayerContent(filters, onFavoriteFilterChanged)
            },
            frontLayerShape = CutCornerShape(topLeft = 32.dp),
            peekHeight = 104.dp,
            appBar = {
                TopAppBar(
                    title = { Text("DroidKaigi") },
                    elevation = 0.dp,
                    navigationIcon = {
                        IconButton(onClick = onNavigationIconClick) {
                            Icon(vectorResource(R.drawable.ic_baseline_menu_24))
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
                                                    topLeft = 8.dp,
                                                    bottomRight = 8.dp
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
            },
            frontLayerContent = {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    NewsList(
                        newsContents = if (selectedTab is NewsTabs.FilteredNews) {
                            newsContents.filterNewsType(selectedTab.newsClass)
                        } else {
                            newsContents
                        },
                        onClickNews = onClickNews,
                        onFavoriteChange = onFavoriteChange
                    )
                }
            }
        )
    }
}

@Composable
private fun NewsList(
    newsContents: NewsContents,
    onClickNews: (News) -> Unit,
    onFavoriteChange: (News) -> Unit,
) {
    LazyColumn {
        if (newsContents.size > 0) {
            items(newsContents.contents) { (item, favorited) ->
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                NewsItem(
                    news = item,
                    favorited = favorited,
                    onClick = onClickNews,
                    onFavoriteChange = onFavoriteChange
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsScreenPreview() {
    Conferenceapp2021newsTheme(false) {
        ProvideNewsViewModel(viewModel = fakeNewsViewModel()) {
            NewsScreen(
                {
                },
                { news: News ->
                }
            )
        }
    }
}
