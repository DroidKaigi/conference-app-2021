package io.github.droidkaigi.confsched2021.news

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.lazy.ExperimentalLazyDsl
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import io.github.droidkaigi.confsched2021.news.ui.Conferenceapp2021newsTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun ComponentActivity.setup(viewModel: INewsViewModel) {
    setContent {
        Providers(NewsViewModelAmbient provides viewModel) {
            NewsApp()
        }
    }
}

@OptIn(ExperimentalLazyDsl::class)
@Composable
fun NewsApp(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    Conferenceapp2021newsTheme {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                Text("this is drawer")
            },
            topBar = {
                TopAppBar(
                    title = { Text("DroidKaigi News") },
                    navigationIcon = {
                        IconButton(onClick = { scaffoldState.drawerState.open() }) {
//                            Icon(vectorResource(R.drawable))
                        }
                    }
                )
            },
            bodyContent = {
                Surface(color = MaterialTheme.colors.background) {
                    val newsViewModel = newsViewModel()
                    val articles: Articles by newsViewModel.articles.collectAsState(initial = Articles())
                    LazyColumn {
                        item {
                            BigArticleItem(articles.bigArticle)
                        }
                        items(articles.remainArticles) { item ->
                            ArticleItem(item)
                        }
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Conferenceapp2021newsTheme {
        Providers(NewsViewModelAmbient provides object : INewsViewModel {
            override val articles: Flow<Articles>
                get() = flowOf(
                    Articles(
                        listOf(

                        )
                    )
                )

        }) {
            NewsApp()
        }
    }
}