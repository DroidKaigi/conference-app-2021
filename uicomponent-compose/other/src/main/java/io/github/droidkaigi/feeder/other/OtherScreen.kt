package io.github.droidkaigi.feeder.other

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.about.AboutThisApp
import io.github.droidkaigi.feeder.contributor.ContributorList
import io.github.droidkaigi.feeder.core.ScrollableTabRow
import io.github.droidkaigi.feeder.core.TabIndicator
import io.github.droidkaigi.feeder.core.TabRowDefaults.tabIndicatorOffset
import io.github.droidkaigi.feeder.core.animation.FadeThrough
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.staff.StaffList

sealed class OtherTabs(val name: String, val routePath: String) {
    object AboutThisApp : OtherTabs("About", "about")
    object Contributor : OtherTabs("Contributor", "contributor")
    object Staff : OtherTabs("Staff", "about_this_app")
    object Settings : OtherTabs("Setting", "setting")

    companion object {
        fun values() = listOf(AboutThisApp, Contributor, Staff, Settings)
        fun ofRoutePath(routePath: String) =
            values().find { it.routePath == routePath } ?: AboutThisApp
    }
}

/**
 * stateful
 */
@Composable
fun OtherScreen(
    selectedTab: OtherTabs,
    onSelectTab: (OtherTabs) -> Unit,
    onNavigationIconClick: () -> Unit,
    onClickContributor: (Contributor) -> Unit,
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)

    OtherScreen(
        scaffoldState = scaffoldState,
        selectedTab = selectedTab,
        onSelectTab = onSelectTab,
        onNavigationIconClick = onNavigationIconClick,
        onClickContributor = onClickContributor
    )
}

/**
 * stateless
 */
@Composable
fun OtherScreen(
    scaffoldState: BackdropScaffoldState,
    selectedTab: OtherTabs,
    onSelectTab: (OtherTabs) -> Unit,
    onNavigationIconClick: () -> Unit,
    onClickContributor: (Contributor) -> Unit,
) {
    Column {
        val density = LocalDensity.current
        BackdropScaffold(
            gesturesEnabled = false,
            backLayerBackgroundColor = MaterialTheme.colors.primarySurface,
            scaffoldState = scaffoldState,
            backLayerContent = {
                Box(modifier = Modifier.height(1.dp))
            },
            frontLayerShape = MaterialTheme.shapes.large,
            peekHeight = 104.dp + (LocalWindowInsets.current.systemBars.top / density.density).dp,
            appBar = {
                AppBar(onNavigationIconClick, selectedTab, onSelectTab)
            },
            frontLayerContent = {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    FadeThrough(targetState = selectedTab) { selectedTab ->
                        BackdropFrontLayerContent(selectedTab, onClickContributor)
                    }
                }
            }
        )
    }
}

@Composable
private fun AppBar(
    onNavigationIconClick: () -> Unit,
    selectedTab: OtherTabs,
    onSelectTab: (OtherTabs) -> Unit,
) {
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = { Image(painterResource(R.drawable.toolbar_droidkaigi_logo), "DroidKaigi") },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(painterResource(R.drawable.ic_baseline_menu_24), "menu")
            }
        }
    )
    val selectedTabIndex = OtherTabs.values().indexOf(selectedTab)
    ScrollableTabRow(
        selectedTabIndex = 0,
        edgePadding = 0.dp,
        foregroundIndicator = {},
        backgroundIndicator = { tabPositions ->
            TabIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
            )
        },
        divider = {}
    ) {
        OtherTabs.values().forEach { tab ->
            Tab(
                selected = tab == selectedTab,
                text = {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        text = tab.name
                    )
                },
                onClick = { onSelectTab(tab) }
            )
        }
    }
}

@Composable
private fun BackdropFrontLayerContent(
    selectedTab: OtherTabs,
    onClickContributor: (Contributor) -> Unit,
) {
    when (selectedTab) {
        OtherTabs.AboutThisApp -> AboutThisApp()
        OtherTabs.Contributor -> ContributorList(onClickContributor)
        OtherTabs.Staff -> StaffList()
        else -> {
            val context = LocalContext.current
            Text(
                text = "Not implemented yet. Please create this screen!",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp)
                    .clickable {
                        val issue =
                            "https://github.com/DroidKaigi/" +
                                "conference-app-2021/issues" +
                                "?q=is%3Aissue+is%3Aopen+label%3Awelcome_contribute"
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(
                                    issue
                                )
                            )
                        )
                    }
            )
        }
    }
}

@Preview
@Composable
fun PreviewOtherScreen() {
    ConferenceAppFeederTheme {
        OtherScreen(
            selectedTab = OtherTabs.AboutThisApp,
            onSelectTab = {
            },
            onNavigationIconClick = {
            },
            onClickContributor = {
            }
        )
    }
}
