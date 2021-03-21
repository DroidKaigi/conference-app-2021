package io.github.droidkaigi.feeder.other

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.about.AboutThisApp
import io.github.droidkaigi.feeder.contributor.ContributorList
import io.github.droidkaigi.feeder.core.ScrollableTabRow
import io.github.droidkaigi.feeder.core.TabIndicator
import io.github.droidkaigi.feeder.core.TabRowDefaults.tabIndicatorOffset
import io.github.droidkaigi.feeder.core.animation.FadeThrough
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.setting.Settings
import io.github.droidkaigi.feeder.staff.StaffList

sealed class OtherTab(val name: String, val routePath: String) {
    object AboutThisApp : OtherTab("About", "about")
    object Contributor : OtherTab("Contributor", "contributor")
    object Staff : OtherTab("Staff", "about_this_app")
    object Settings : OtherTab("Setting", "setting")

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
    selectedTab: OtherTab,
    onSelectTab: (OtherTab) -> Unit,
    onNavigationIconClick: () -> Unit,
    onContributorClick: (Contributor) -> Unit,
    onStaffClick: (Staff) -> Unit,
    onPrivacyPolicyClick: (String) -> Unit,
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)

    OtherScreen(
        scaffoldState = scaffoldState,
        selectedTab = selectedTab,
        onSelectTab = onSelectTab,
        onNavigationIconClick = onNavigationIconClick,
        onContributorClick = onContributorClick,
        onStaffClick = onStaffClick,
        onPrivacyPolicyClick = onPrivacyPolicyClick
    )
}

/**
 * stateless
 */
@Composable
fun OtherScreen(
    scaffoldState: BackdropScaffoldState,
    selectedTab: OtherTab,
    onSelectTab: (OtherTab) -> Unit,
    onNavigationIconClick: () -> Unit,
    onContributorClick: (Contributor) -> Unit,
    onStaffClick: (Staff) -> Unit,
    onPrivacyPolicyClick: (String) -> Unit,
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
                        BackdropFrontLayerContent(selectedTab, onContributorClick, onStaffClick, onPrivacyPolicyClick)
                    }
                }
            }
        )
    }
}

@Composable
private fun AppBar(
    onNavigationIconClick: () -> Unit,
    selectedTab: OtherTab,
    onSelectTab: (OtherTab) -> Unit,
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
    val selectedTabIndex = OtherTab.values().indexOf(selectedTab)
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
        OtherTab.values().forEach { tab ->
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
    selectedTab: OtherTab,
    onContributorClick: (Contributor) -> Unit,
    onStaffClick: (Staff) -> Unit,
    onPrivacyPolicyClick: (String) -> Unit,
) {
    when (selectedTab) {
        OtherTab.AboutThisApp -> AboutThisApp(onPrivacyPolicyClick)
        OtherTab.Contributor -> ContributorList(onContributorClick)
        OtherTab.Settings -> Settings()
        OtherTab.Staff -> StaffList(onStaffClick)
    }
}

@Preview
@Composable
fun PreviewOtherScreen() {
    ConferenceAppFeederTheme {
        OtherScreen(
            selectedTab = OtherTab.AboutThisApp,
            onSelectTab = {
            },
            onNavigationIconClick = {
            },
            onContributorClick = {
            },
            onStaffClick = {
            },
            onPrivacyPolicyClick = {
            }
        )
    }
}
