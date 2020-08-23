package io.github.droidkaigi.confsched2021.news

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.ExperimentalLazyDsl
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import io.github.droidkaigi.confsched2021.news.ui.Conferenceapp2021newsTheme
import io.github.droidkaigi.confsched2021.news.uicomponent.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

val ScaffoldStateAmbient = ambientOf<ScaffoldState>()

@Composable
fun NewsApp() {
    Conferenceapp2021newsTheme {
        Providers(ScaffoldStateAmbient provides rememberScaffoldState()) {
            Scaffold()
        }
    }
}

@OptIn(ExperimentalLazyDsl::class)
@Composable
private fun Scaffold() {
    val scaffoldState = ScaffoldStateAmbient.current
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
                        Icon(imageResource(R.drawable.ic_logo))
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = {
                        Image(vectorResource(R.drawable.ic_baseline_home_24))
                    },
                    label = {
                        Text("Home")
                    },
                    selected = true,
                    onSelect = {

                    }
                )
                BottomNavigationItem(
                    icon = {
                        Image(vectorResource(R.drawable.ic_baseline_favorite_24))
                    },
                    label = {
                        Text("Favorite")
                    },
                    selected = false,
                    onSelect = {

                    }
                )
            }
        },
        bodyContent = { padding ->
            Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxHeight()
            ) {
                val newsViewModel = newsViewModel()
                val articles: Articles by newsViewModel.articles.collectAsState(initial = Articles())
                LazyColumn(Modifier.padding(bottom = padding.bottom)) {
                    item {
                        BigArticleItem(articles.bigArticle)
                    }
                    items(articles.remainArticles) { item ->
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                        ArticleItem(item)
                    }
                }
            }
        }
    )
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