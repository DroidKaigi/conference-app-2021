package io.github.droidkaigi.confnews2021.staff

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawerLayout
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import io.github.droidkaigi.confnews2021.News
import io.github.droidkaigi.confnews2021.other.OtherScreen
import io.github.droidkaigi.confnews2021.other.OtherTabs
import io.github.droidkaigi.confnews2021.staff.news.NewsScreen
import io.github.droidkaigi.confnews2021.staff.news.NewsTabs

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
        drawerContent = {
            DrawerContent { route ->
                try {
                    navController.navigate(route)
                } finally {
                    drawerState.close()
                }
            }
        }
    ) {
        NavHost(navController, startDestination = "news/{newsTab}") {
            composable(
                route = "news/{newsTab}",
                arguments = listOf(
                    navArgument("newsTab") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val newsType = backStackEntry
                    .arguments?.getString("newsTab") ?: NewsTabs.Home.routePath
                val context = AmbientContext.current
                NewsScreen(
                    onNavigationIconClick = onNavigationIconClick,
                    initialSelectedTab = NewsTabs.ofRoutePath(newsType),
                    onDetailClick = { news: News ->
                        // FIXME: Use navigation
                        val builder = CustomTabsIntent.Builder()
                            .setShowTitle(true)
                            .setUrlBarHidingEnabled(true)

                        val intent = builder.build()
                        intent.launchUrl(context, Uri.parse(news.link))
                    }
                )
            }
            composable(
                route = "other/{otherTab}",
                arguments = listOf(
                    navArgument("otherTab") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val routePath = backStackEntry
                    .arguments?.getString("otherTab") ?: NewsTabs.Home.routePath
                OtherScreen(
                    OtherTabs.ofRoutePath(routePath),
                    onNavigationIconClick
                )
            }
        }
    }
}
