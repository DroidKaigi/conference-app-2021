package io.github.droidkaigi.feeder

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.DrawerDefaults
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.navDeepLink
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.github.droidkaigi.feeder.core.R as CoreR
import io.github.droidkaigi.feeder.core.navigation.chromeCustomTabs
import io.github.droidkaigi.feeder.core.navigation.navigateChromeCustomTabs
import io.github.droidkaigi.feeder.core.navigation.rememberCustomNavController
import io.github.droidkaigi.feeder.feed.FeedScreen
import io.github.droidkaigi.feeder.feed.FeedTab
import io.github.droidkaigi.feeder.other.OtherScreen
import io.github.droidkaigi.feeder.other.OtherTab
import io.github.droidkaigi.feeder.timetable2021.TimetableDetailScreen
import io.github.droidkaigi.feeder.timetable2021.TimetableScreen
import io.github.droidkaigi.feeder.timetable2021.TimetableTab
import kotlinx.coroutines.launch

private const val FEED_PATH = "feed/"
private const val OTHER_PATH = "other/"
private const val TIMETABLE_PATH = "timetable/"
private const val TIMETABLE_DETAIL_PATH = "timetable/detail/"

private val drawerOpenedStatusBarColor = Color.Black.copy(alpha = 0.48f)

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    firstDrawerValue: DrawerValue = DrawerValue.Closed,
) {
    val systemUiController = rememberSystemUiController()
    val drawerState = rememberDrawerState(firstDrawerValue)
    LaunchedEffect(drawerState.currentValue) {
        if (drawerState.currentValue == DrawerValue.Closed) {
            systemUiController.setStatusBarColor(Color.Transparent)
        } else {
            systemUiController.setStatusBarColor(drawerOpenedStatusBarColor)
        }
    }
    val drawerContentState = rememberDrawerContentState(DrawerContents.HOME.route)
    val navController = rememberCustomNavController()
    val coroutineScope = rememberCoroutineScope()
    val onNavigationIconClick: () -> Unit = {
        coroutineScope.launch {
            drawerState.open()
        }
    }
    val deepLinkUri =
        "https://" + LocalContext.current.getString(CoreR.string.deep_link_host) +
            LocalContext.current.getString(CoreR.string.deep_link_path)
    val actions = remember(navController) {
        AppActions(navController)
    }
    ModalDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerShape = MaterialTheme.shapes.large.copy(all = CornerSize(0.dp)),
        drawerContent = {
            DrawerContent(drawerContentState.currentValue) { contents ->
                if (drawerContentState.selectDrawerContent(contents.route)) {
                    actions.onSelectDrawerItem(contents)
                }
                coroutineScope.launch {
                    drawerState.close()
                }
            }
        },
        scrimColor = Color.Black.copy(alpha = DrawerDefaults.ScrimOpacity),
    ) {
        NavHost(navController, startDestination = "$FEED_PATH{feedTab}") {
            composable(
                route = "$FEED_PATH{feedTab}",
                deepLinks = listOf(navDeepLink { uriPattern = "$deepLinkUri/$FEED_PATH{feedTab}" }),
                arguments = listOf(
                    navArgument("feedTab") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val routePath = rememberRoutePath(
                    backStackEntry.arguments?.getString("feedTab")
                        ?: FeedTab.Home.routePath
                )
                val selectedTab = FeedTab.ofRoutePath(routePath.value)
                drawerContentState.onSelectDrawerContent(selectedTab)
                FeedScreen(
                    onNavigationIconClick = onNavigationIconClick,
                    selectedTab = selectedTab,
                    onSelectedTab = { feedTab ->
                        // We don't use navigation component transitions here for animation.
                        routePath.value = feedTab.routePath
                        drawerContentState.onSelectDrawerContent(feedTab)
                    },
                    onDetailClick = { feedItem: FeedItem ->
                        actions.showChromeCustomTabs(feedItem.link)
                    }
                )
            }
            composable(
                route = "$TIMETABLE_PATH{timeTable}",
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "$deepLinkUri/$TIMETABLE_PATH{timeTable}"
                    }
                ),
                arguments = listOf(
                    navArgument("timeTable") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val routePath = rememberRoutePath(
                    backStackEntry.arguments?.getString("timeTable")
                        ?: OtherTab.AboutThisApp.routePath
                )
                val selectedTab = TimetableTab.ofRoutePath(routePath.value)
                drawerContentState.onSelectDrawerContent(selectedTab)
                TimetableScreen(
                    selectedTab = selectedTab,
                    onNavigationIconClick = onNavigationIconClick,
                    onSelectedTab = {},
                    onDetailClick = { id ->
                        actions.onSelectTimetableDetail(id)
                    },
                )
            }
            composable(
                route = "$TIMETABLE_DETAIL_PATH{id}",
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "$deepLinkUri/$TIMETABLE_DETAIL_PATH/{id}"
                    }
                ),
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val routePath = rememberRoutePath(
                    backStackEntry.arguments?.getString("id") ?: ""
                )
                val id = routePath.value
                TimetableDetailScreen(
                    id = id,
                    onNavigationIconClick = onNavigationIconClick,
                )
            }
            composable(
                route = "$OTHER_PATH{otherTab}",
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "$deepLinkUri/$OTHER_PATH{otherTab}"
                    }
                ),
                arguments = listOf(
                    navArgument("otherTab") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                val routePath = rememberRoutePath(
                    backStackEntry.arguments?.getString("otherTab")
                        ?: OtherTab.AboutThisApp.routePath
                )
                val selectedTab = OtherTab.ofRoutePath(routePath.value)
                drawerContentState.onSelectDrawerContent(selectedTab)
                OtherScreen(
                    selectedTab = selectedTab,
                    onSelectTab = { otherTab ->
                        // We don't use navigation component transitions here for animation.
                        routePath.value = otherTab.routePath
                        drawerContentState.onSelectDrawerContent(otherTab)
                    },
                    onNavigationIconClick = onNavigationIconClick,
                    onContributorClick = { contributor ->
                        actions.showChromeCustomTabs(contributor.url)
                    },
                    onStaffClick = { staff ->
                        actions.showChromeCustomTabs(staff.profileUrl)
                    },
                    onPrivacyPolicyClick = {
                        actions.showChromeCustomTabs(it)
                    }
                )
            }
            chromeCustomTabs()
        }
    }
}

private class AppActions(navController: NavHostController) {
    val onSelectDrawerItem: (DrawerContents) -> Unit = { contents ->
        navController.navigate(contents.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items.
            // And clean up all of the stacks if users select one of feed tabs.
            // Refer to https://developer.android.com/jetpack/compose/navigation#bottom-nav
            popUpTo(navController.graph.startDestinationId) {
                inclusive = when (contents) {
                    DrawerContents.HOME,
                    DrawerContents.BLOG,
                    DrawerContents.VIDEO,
                    DrawerContents.PODCAST,
                    -> true
                    else -> false
                }
            }
        }
    }

    val onSelectTimetableDetail: (String) -> Unit = { id ->
        navController.navigate(TIMETABLE_DETAIL_PATH + id)
    }

    val showChromeCustomTabs: (String) -> Unit = { link ->
        navController.navigateChromeCustomTabs(link)
    }
}

@Composable
fun rememberRoutePath(
    initialRoutePath: String,
) = rememberSaveable {
    mutableStateOf(initialRoutePath)
}
