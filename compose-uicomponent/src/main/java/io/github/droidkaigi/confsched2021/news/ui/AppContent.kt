package io.github.droidkaigi.confsched2021.news.ui

import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawerLayout
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.ui.news.NewsScreen

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    firstDrawerValue: DrawerValue = DrawerValue.Closed,
) {
    val drawerState = rememberDrawerState(firstDrawerValue)
    val navController = rememberNavController()
    val onNavigationIconClick = { drawerState.open() }
    ModalDrawerLayout(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = { DrawerContent(navController) }
    ) {
        NavHost(navController, startDestination = "news/list") {
            composable("news/list") {
                NewsScreen(onNavigationIconClick, { news: News ->
                    navController.navigate("news_detail")
                })
            }
            composable("news/{newsId}", listOf(navArgument("newsId") {
                type = NavType.StringType
            })) { backStackEntry ->
                Text("detail of:${backStackEntry.arguments?.getString("newsId")}")
            }
            composable("about_this_app") {
                Text("ok?")
            }
            composable("about_this_app") {
                Text("ok?")
            }
        }
    }
}
