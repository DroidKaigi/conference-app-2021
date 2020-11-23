package io.github.droidkaigi.confsched2021.news.ui.article

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.NewsContents
import io.github.droidkaigi.confsched2021.news.ui.ProvideNewsViewModel
import io.github.droidkaigi.confsched2021.news.ui.fakeNewsViewModel
import io.github.droidkaigi.confsched2021.news.ui.newsViewModel
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
    onNavigationIconClick: () -> Unit
) {
    val newsViewModel = newsViewModel()
    val newsContents: NewsContents by newsViewModel.newsContents.collectAsState(
        initial = newsViewModel.newsContents.value
    )
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val selectedTab = remember<MutableState<NewsTabs>> { mutableStateOf(NewsTabs.Home) }
    val onSelectTab = { tab: NewsTabs ->
        selectedTab.value = tab
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
    val onFavoriteChange: (News) -> Unit = {
        newsViewModel.onToggleFavorite(it)
    }
    NewsScreen(
        newsContents = newsContents,
        selectedTab = selectedTab,
        onSelectTab = onSelectTab,
        scaffoldState = scaffoldState,
        onNavigationIconClick = onNavigationIconClick,
        onClickNews = onClickNews,
        onFavoriteChange = onFavoriteChange
    )
}


/**
 * stateless
 */
@Composable
private fun NewsScreen(
    newsContents: NewsContents,
    scaffoldState: BackdropScaffoldState,
    selectedTab: MutableState<NewsTabs>,
    onSelectTab: (NewsTabs) -> Unit,
    onNavigationIconClick: () -> Unit,
    onClickNews: (News) -> Unit,
    onFavoriteChange: (News) -> Unit
) {
    Column {
        BackdropScaffold(
            backLayerBackgroundColor = MaterialTheme.colors.primary,
            scaffoldState = scaffoldState,
            backLayerContent = {
                BackLayerContent()
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
                    val selectedTabValue = selectedTab.value
                    NewsList(
                        newsContents = if (selectedTabValue is NewsTabs.FilteredNews) {
                            newsContents.filterNewsType(selectedTabValue.newsClass)
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
    onFavoriteChange: (News) -> Unit
) {
    LazyColumn {
        if (newsContents.size > 0) {
            items(newsContents.contents) { (item, favorited) ->
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                NewsItem(
                    article = item,
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
        ProvideNewsViewModel(viewModel = fakeNewsViewModel()){
            NewsScreen {

            }
        }
    }
}

