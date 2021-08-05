package io.github.droidkaigi.feeder.core.navigation

import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.navOptions
import java.net.URLEncoder

internal const val KEY_ROUTE = "android-support-nav:controller:chrome"

@Composable
public fun rememberCustomNavController(): NavHostController {
    val context = LocalContext.current
    return rememberSaveable(saver = CustomNavControllerSaver(context)) {
        createCustomNavController(context)
    }
}

private fun createCustomNavController(context: Context) =
    NavHostController(context).apply {
        navigatorProvider.addNavigator(ChromeCustomTabsNavigator(context))
        navigatorProvider.addNavigator(DialogNavigator())
        navigatorProvider.addNavigator(ComposeNavigator())
    }

private fun CustomNavControllerSaver(
    context: Context,
): Saver<NavHostController, *> = Saver<NavHostController, Bundle>(
    save = { it.saveState() },
    restore = { createCustomNavController(context).apply { restoreState(it) } }
)

fun NavController.navigateChromeCustomTabs(
    url: String,
    builder: NavOptionsBuilder.() -> Unit = {},
) {
    navigate(
        NavDeepLinkRequest.Builder.fromUri(
            createRoute("chrome/${URLEncoder.encode(url, "utf-8")}")
                .toUri()
        ).build(),
        navOptions(builder)
    )
}

internal fun createRoute(route: String) = "android-app://androidx.navigation.chrome/$route"
