package io.github.droidkaigi.confsched2021.news

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
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

@Composable
fun NewsApp() {
    Conferenceapp2021newsTheme {
        Surface(color = MaterialTheme.colors.background) {
            val newsViewModel = newsViewModel()
            val articles: Articles by newsViewModel.articles.collectAsState(initial = Articles())
            Row {
                LazyColumnFor(articles.allArticles) {
                    ArticleItem(it)
                }
            }

        }
    }
}

val NewsViewModelAmbient = ambientOf<INewsViewModel>()

@Composable
fun newsViewModel() = NewsViewModelAmbient.current

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