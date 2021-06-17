package io.github.droidkaigi.feeder.other

import androidx.compose.foundation.Image
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
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.about.AboutThisApp
import io.github.droidkaigi.feeder.contributor.ContributorList
import io.github.droidkaigi.feeder.core.TabIndicator
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.setting.Settings
import io.github.droidkaigi.feeder.staff.StaffList
import kotlinx.coroutines.launch

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
@OptIn(ExperimentalPagerApi::class)
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
    val pagerState = rememberPagerState(
        pageCount = OtherTab.values().size,
        initialPage = OtherTab.values().indexOf(selectedTab)
    )

    OtherScreen(
        scaffoldState = scaffoldState,
        pagerState = pagerState,
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
@OptIn(ExperimentalPagerApi::class)
@Composable
fun OtherScreen(
    scaffoldState: BackdropScaffoldState,
    pagerState: PagerState,
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
                AppBar(pagerState, onNavigationIconClick, onSelectTab)
            },
            frontLayerContent = {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    BackdropFrontLayerContent(
                        pagerState, onContributorClick, onStaffClick, onPrivacyPolicyClick
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun AppBar(
    pagerState: PagerState,
    onNavigationIconClick: () -> Unit,
    onSelectTab: (OtherTab) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = { Image(painterResource(io.github.droidkaigi.feeder.core.R.drawable.toolbar_droidkaigi_logo), "DroidKaigi") },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(painterResource(io.github.droidkaigi.feeder.core.R.drawable.ic_baseline_menu_24), "menu")
            }
        }
    )
    ScrollableTabRow(
        selectedTabIndex = 0,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabIndicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        divider = {}
    ) {
        OtherTab.values().forEachIndexed { index, tab ->
            Tab(
                selected = index == pagerState.currentPage,
                text = {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        text = tab.name
                    )
                },
                onClick = {
                    onSelectTab(tab)
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                // For tabs to draw in front of indicators
                modifier = Modifier.zIndex(1f)
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun BackdropFrontLayerContent(
    pagerState: PagerState,
    onContributorClick: (Contributor) -> Unit,
    onStaffClick: (Staff) -> Unit,
    onPrivacyPolicyClick: (String) -> Unit,
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Top
    ) { page ->
        when (OtherTab.values()[page]) {
            OtherTab.AboutThisApp -> AboutThisApp(onPrivacyPolicyClick)
            OtherTab.Contributor -> ContributorList(onContributorClick)
            OtherTab.Settings -> Settings()
            OtherTab.Staff -> StaffList(onStaffClick)
        }
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
