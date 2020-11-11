package io.github.droidkaigi.confsched2021.news.article

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.ExperimentalLazyDsl
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.Divider
import androidx.compose.material.DrawerState
import androidx.compose.material.ExperimentalMaterialApi
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
import io.github.droidkaigi.confsched2021.news.Articles
import io.github.droidkaigi.confsched2021.news.newsViewModel
import io.github.droidkaigi.confsched2021.news.uicomponent.R
import kotlinx.coroutines.launch

/**
 * stateful
 */
@OptIn(ExperimentalLazyDsl::class, ExperimentalMaterialApi::class)
@Composable
fun ArticleScreen(
    drawerState: DrawerState,
    firstBackdropValue: BackdropValue = BackdropValue.Concealed
) {
    val newsViewModel = newsViewModel()
    val articles: Articles by newsViewModel.articles.collectAsState(initial = Articles())
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBackdropScaffoldState(firstBackdropValue)
    val onClickArticle: () -> Unit = {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                "TODO: waiting " +
                        "navigation " +
                        "component"
            )
        }
    }
    val onNavigationIconClick = { drawerState.open() }
    ArticleScreen(articles, scaffoldState, onNavigationIconClick, onClickArticle)
}

/**
 * stateless
 */
@OptIn(ExperimentalLazyDsl::class, ExperimentalMaterialApi::class)
@Composable
private fun ArticleScreen(
    articles: Articles,
    scaffoldState: BackdropScaffoldState,
    onNavigationIconClick: () -> Unit,
    onClickArticle: () -> Unit
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
                LazyColumn {
                    if (articles.size > 0) {
                        item {
                            BigArticleItem(articles.bigArticle, onClick = onClickArticle)
                        }
                        items(articles.remainArticles) { item ->
                            Divider(modifier = Modifier.padding(horizontal = 16.dp))
                            ArticleItem(item, onClickArticle)
                        }
                    }
                }
            }
        }
    )
}