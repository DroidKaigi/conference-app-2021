package io.github.droidkaigi.confsched2021.news.ui

import androidx.compose.foundation.Text
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawerLayout
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.ui.tooling.preview.Preview
import io.github.droidkaigi.confsched2021.news.Article
import io.github.droidkaigi.confsched2021.news.Articles
import io.github.droidkaigi.confsched2021.news.Filters
import io.github.droidkaigi.confsched2021.news.ui.article.ArticleScreen
import io.github.droidkaigi.confsched2021.news.ui.theme.Conferenceapp2021newsTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    firstDrawerValue: DrawerValue = DrawerValue.Closed,
) {
    val drawerState = rememberDrawerState(firstDrawerValue)
    val navController = rememberNavController()
    ModalDrawerLayout(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = { DrawerContent(navController) }
    ) {
        NavHost(navController, startDestination = "articles") {
            composable("articles") {
                ArticleScreen(drawerState)
            }
            composable("about_this_app") { Text("ok?") }
        }
    }
}



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
            AppContent()
        }
    }
}

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
            AppContent()
        }
    }
}
