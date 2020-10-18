package io.github.droidkaigi.confsched2021.news

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.ExperimentalLazyDsl
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.Divider
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawerLayout
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import io.github.droidkaigi.confsched2021.news.ui.Conferenceapp2021newsTheme
import io.github.droidkaigi.confsched2021.news.uicomponent.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterialApi::class)
val ScaffoldStateAmbient = ambientOf<BackdropScaffoldState>()

@OptIn(ExperimentalLazyDsl::class, ExperimentalMaterialApi::class)
@Composable
fun NewsHome(
    modifier: Modifier = Modifier,
    firstDrawerValue: DrawerValue = DrawerValue.Closed,
    firstBackdropValue: BackdropValue = BackdropValue.Concealed
) {
    val drawerState = rememberDrawerState(firstDrawerValue)
    val scaffoldState =
        ScaffoldStateAmbient provides rememberBackdropScaffoldState(firstBackdropValue)
    ModalDrawerLayout(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = { DrawerContent() }
    ) {
        Providers(scaffoldState) {
            val scaffoldState = ScaffoldStateAmbient.current
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
                            IconButton(onClick = { drawerState.open() }) {
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
                        val newsViewModel = newsViewModel()
                        val articles: Articles by newsViewModel.articles.collectAsState(initial = Articles())
                        LazyColumn {
                            if (articles.size > 0) {
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
                }
            )
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Preview(showBackground = true)
@Composable
fun NewsHomePreview() {
    Conferenceapp2021newsTheme {
        ProvideNewsViewModel(viewModel = object : INewsViewModel {
            override val filter: StateFlow<Filters> = MutableStateFlow(Filters())
            override val articles: StateFlow<Articles> = MutableStateFlow(Articles())
            override fun onFilterChanged(filters: Filters) {
            }

            override fun toggleFavorite(article: Article) {
            }
        }) {
            NewsHome(firstBackdropValue = BackdropValue.Revealed)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Preview(showBackground = true)
@Composable
fun DarkThemeNewsHomePreview() {
    Conferenceapp2021newsTheme(true) {
        ProvideNewsViewModel(viewModel = object : INewsViewModel {
            override val filter: StateFlow<Filters> = MutableStateFlow(Filters())
            override val articles: StateFlow<Articles> = MutableStateFlow(Articles())
            override fun onFilterChanged(filters: Filters) {
            }

            override fun toggleFavorite(article: Article) {
            }
        }) {
            NewsHome(firstBackdropValue = BackdropValue.Revealed)
        }
    }
}
