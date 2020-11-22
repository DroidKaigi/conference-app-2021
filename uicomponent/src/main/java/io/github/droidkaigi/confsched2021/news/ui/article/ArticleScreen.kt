package io.github.droidkaigi.confsched2021.news.ui.article

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.Divider
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.NewsContents
import io.github.droidkaigi.confsched2021.news.ui.newsViewModel
import io.github.droidkaigi.confsched2021.news.uicomponent.R
import kotlinx.coroutines.launch

/**
 * stateful
 */
@Composable
fun ArticleScreen(
    drawerState: DrawerState,
    firstBackdropValue: BackdropValue = BackdropValue.Concealed
) {
    val newsViewModel = newsViewModel()
    val newsContents: NewsContents by newsViewModel.newsContents.collectAsState(initial = NewsContents())
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBackdropScaffoldState(firstBackdropValue)
    val onClickArticle: (News) -> Unit = {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                "TODO: waiting " +
                        "navigation " +
                        "component"
            )
        }
    }
    val onNavigationIconClick = { drawerState.open() }
    val onFavoriteChange: (News) -> Unit = {
        newsViewModel.onToggleFavorite(it)
    }
    ArticleScreen(
        newsContents = newsContents,
        scaffoldState = scaffoldState,
        onNavigationIconClick = onNavigationIconClick,
        onClickArticle = onClickArticle,
        onFavoriteChange = onFavoriteChange
    )
}

/**
 * stateless
 */
@Composable
private fun ArticleScreen(
    newsContents: NewsContents,
    scaffoldState: BackdropScaffoldState,
    onNavigationIconClick: () -> Unit,
    onClickArticle: (News) -> Unit,
    onFavoriteChange: (News) -> Unit
) {
    BackdropScaffold(
        backLayerBackgroundColor = MaterialTheme.colors.primary,
        scaffoldState = scaffoldState,
        backLayerContent = {
            BackLayerContent()
        },
        appBar = {
            TopAppBar(
                title = { Text("DroidKaigi News") },
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = onNavigationIconClick) {
                        Icon(imageResource(R.drawable.ic_logo))
                    }
                }
            )
        },
        frontLayerContent = {
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxHeight()
            ) {
                ArticleList(newsContents, onClickArticle, onFavoriteChange)
            }
        }
    )
}

@Composable
private fun ArticleList(
    newsContents: NewsContents,
    onClickArticle: (News) -> Unit,
    onFavoriteChange: (News) -> Unit
) {
    LazyColumn {
        if (newsContents.size > 0) {
            items(newsContents.contents) { (item, favorited) ->
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                ArticleItem(
                    article = item,
                    favorited = favorited,
                    onClick = onClickArticle,
                    onFavoriteChange = onFavoriteChange
                )
            }
        }
    }
}