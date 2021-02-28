package io.github.droidkaigi.feeder

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import io.github.droidkaigi.feeder.feed.FeedScreen
import io.github.droidkaigi.feeder.feed.FeedTabs
import io.github.droidkaigi.feeder.other.OtherScreen
import io.github.droidkaigi.feeder.other.OtherTabs
import kotlinx.coroutines.launch

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    firstDrawerValue: DrawerValue = DrawerValue.Closed,
) {
    val drawerState = rememberDrawerState(firstDrawerValue)
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val onNavigationIconClick: () -> Unit = {
        coroutineScope.launch {
            drawerState.open()
        }
    }
    ModalDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerShape = MaterialTheme.shapes.large.copy(all = CornerSize(0.dp)),
        drawerContent = {
            DrawerContent { route ->
                try {
                    navController.navigate(route)
                } finally {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }
            }
        }
    ) {
        NavHost(navController, startDestination = "feed/{feedTab}") {
            composable(
                route = "feed/{feedTab}",
                arguments = listOf(
                    navArgument("feedTab") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val feedType = backStackEntry
                    .arguments?.getString("feedTab") ?: FeedTabs.Home.routePath
                val context = LocalContext.current
                FeedScreen(
                    onNavigationIconClick = onNavigationIconClick,
                    initialSelectedTab = FeedTabs.ofRoutePath(feedType),
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
