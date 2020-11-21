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
import io.github.droidkaigi.confsched2021.news.Article
import io.github.droidkaigi.confsched2021.news.Articles
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
    val articles: Articles by newsViewModel.articles.collectAsState(initial = Articles())
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBackdropScaffoldState(firstBackdropValue)
    val onClickArticle: (Article) -> Unit = {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                "TODO: waiting " +
                        "navigation " +
                        "component"
            )
        }
    }
    val onNavigationIconClick = { drawerState.open() }
    val onFavoriteChange: (Article) -> Unit = {
        newsViewModel.toggleFavorite(articles.bigArticle)
    }
    ArticleScreen(
        articles = articles,
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
    articles: Articles,
    scaffoldState: BackdropScaffoldState,
    onNavigationIconClick: () -> Unit,
    onClickArticle: (Article) -> Unit,
    onFavoriteChange: (Article) -> Unit
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
                ArticleList(articles, onClickArticle, onFavoriteChange)
            }
        }
    )
}

@Composable
private fun ArticleList(
    articles: Articles,
    onClickArticle: (Article) -> Unit,
    onFavoriteChange: (Article) -> Unit
) {
    LazyColumn {
        if (articles.size > 0) {
            item {
                BigArticleItem(
                    articles.bigArticle,
                    onClick = onClickArticle,
                    onFavoriteChange = onFavoriteChange
                )
            }
            items(articles.remainArticles) { item ->
                Divider(modifier = Modifier.padding(horizontal = 16.dp))
                ArticleItem(
                    article = item,
                    onClick = onClickArticle,
                    onFavoriteChange = onFavoriteChange
                )
            }
        }
    }
}