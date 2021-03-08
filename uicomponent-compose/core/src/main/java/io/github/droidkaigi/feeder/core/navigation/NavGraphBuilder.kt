package io.github.droidkaigi.feeder.core.navigation

import androidx.navigation.NavArgument
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavDestinationDsl
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.get
import io.github.droidkaigi.feeder.core.navigation.ChromeCustomTabsNavigator.Companion.ARGUMENT_NAME_URL

fun NavGraphBuilder.chromeCustomTabs() {
    addDestination(
        ChromeCustomTabsNavigator.Destination(provider[ChromeCustomTabsNavigator::class]).apply {
            val route = "chrome/{$ARGUMENT_NAME_URL}"
            val internalRoute = createRoute(route)
            addDeepLink(internalRoute)
            addArgument(KEY_ROUTE, navArgument { defaultValue = route })
            id = internalRoute.hashCode()
            addArgument(ARGUMENT_NAME_URL, navArgument { type = NavType.StringType })
        }
    )
}

@NavDestinationDsl
inline fun navArgument(builder: NavArgumentBuilder.() -> Unit): NavArgument {
    return NavArgumentBuilder().apply(builder).build()
}
