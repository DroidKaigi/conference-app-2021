package io.github.droidkaigi.feeder

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawerLayout
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import io.github.droidkaigi.feeder.other.OtherScreen
import io.github.droidkaigi.feeder.other.OtherTabs
import io.github.droidkaigi.feeder.feed.FeedScreen
import io.github.droidkaigi.feeder.feed.FeedTabs

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
                    .arguments?.getString("newsTab") ?: FeedTabs.Home.routePath
                val context = LocalContext.current
                FeedScreen(
                    onNavigationIconClick = onNavigationIconClick,
                    initialSelectedTab = FeedTabs.ofRoutePath(newsType),
                    onDetailClick = { feedItem: FeedItem ->
                        // FIXME: Use navigation
                        val builder = CustomTabsIntent.Builder()
                            .setShowTitle(true)
                            .setUrlBarHidingEnabled(true)

                        val intent = builder.build()
                        intent.launchUrl(context, Uri.parse(feedItem.link))
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
                    .arguments?.getString("otherTab") ?: FeedTabs.Home.routePath
                OtherScreen(
                    OtherTabs.ofRoutePath(routePath),
                    onNavigationIconClick
                )
            }
        }
    }
}
