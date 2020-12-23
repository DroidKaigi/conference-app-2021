package io.github.droidkaigi.confsched2021.news.ui.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.Divider
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2021.news.Filters
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.NewsContents
import io.github.droidkaigi.confsched2021.news.ui.theme.Conferenceapp2021newsTheme
import kotlinx.coroutines.launch
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
) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    var selectedTab by remember<MutableState<NewsTabs>> { mutableStateOf(NewsTabs.Home) }
    val onSelectTab = { tab: NewsTabs ->
        selectedTab = tab
    }
    val onClickNews: (News) -> Unit = {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                "TODO: waiting " +
                    "navigation " +
                    "component"
            )
        }
    }

    val (
        state,
        onIntent,
    ) = useNewsState()

    val onFavoriteChange: (News) -> Unit = {
        onIntent(NewsViewModel.Intent.ToggleFavorite(it))
    }
    val onFavoriteFilterChanged: (filtered: Boolean) -> Unit = {
        onIntent(NewsViewModel.Intent.ChangeFavoriteFilter(
            state.filters.copy(filterFavorite = !state.filters.filterFavorite))
        )
    }

    NewsScreen(
        selectedTab = selectedTab,
        onSelectTab = onSelectTab,
        scaffoldState = scaffoldState,
        onNavigationIconClick = onNavigationIconClick,
        newsContents = state.filteredNewsContents,
        onFavoriteChange = onFavoriteChange,
        filters = state.filters,
        onFavoriteFilterChanged = onFavoriteFilterChanged,
        onClickNews = onClickNews
    )
}

@Composable
private fun useNewsState(): Pair<NewsViewModel.State, (NewsViewModel.Intent) -> Unit> {
    val newsViewModel = newsViewModel()
    val state by newsViewModel.state.collectAsState()
    val onIntent: (NewsViewModel.Intent) -> Unit = { intent ->
        newsViewModel.intent(intent)
    }
    return state to onIntent
}

/**
 * stateless
 */
@Composable
private fun NewsScreen(
    selectedTab: NewsTabs,
    onSelectTab: (NewsTabs) -> Unit,
    scaffoldState: BackdropScaffoldState,
    onNavigationIconClick: () -> Unit,
    newsContents: NewsContents,
    onFavoriteChange: (News) -> Unit,
    filters: Filters,
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
            peekHeight = 104.dp,
            appBar = {
                TopAppBar(
                    title = { Text("DroidKaigi News") },
                    elevation = 0.dp,
                    navigationIcon = {
                        IconButton(onClick = onNavigationIconClick) {
//                            Icon(vectorResource(R.drawable.ic_baseline_menu_24))
                        }
                    }
                )
                TabRow(selectedTabIndex = 0) {
                    NewsTabs.values().forEach { tab ->
                        Tab(
                            selected = tab == selectedTab,
                            text = { Text(tab.name) },
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
            NewsScreen {
            }
        }
    }
}
