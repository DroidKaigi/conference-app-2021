package io.github.droidkaigi.feeder.timetable2021

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import io.github.droidkaigi.feeder.Theme
import io.github.droidkaigi.feeder.core.R as CoreR
import io.github.droidkaigi.feeder.core.TabIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Stable
data class AppBarState(
    val pagerState: PagerState,
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppBar(
    appBarState: AppBarState,
    onNavigationIconClick: () -> Unit,
    onSelectTab: (TimetableTab) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = {
            Image(
                painterResource(R.drawable.toolbar_droidkaigi_2021_logo),
                "DroidKaigi2021"
            )
        },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(painterResource(CoreR.drawable.ic_baseline_menu_24), "menu")
            }
        }
    )
    ScrollableTabRow(
        selectedTabIndex = 0,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabIndicator(
                modifier = Modifier.pagerTabIndicatorOffset(appBarState.pagerState, tabPositions)
            )
        },
        divider = {}
    ) {
        TimetableTab.values().forEachIndexed { index, tab ->
            val isSelected = index == appBarState.pagerState.currentPage
            Tab(
                selected = isSelected,
                text = {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        text = tab.name,
                    )
                },
                onClick = {
                    onSelectTab(tab)
                    coroutineScope.launch {
                        appBarState.pagerState.animateScrollToPage(index)
                    }
                },
                // For tabs to draw in front of indicators
                modifier = Modifier.zIndex(1f),
                selectedContentColor = MaterialTheme.colors.onSecondary,
                unselectedContentColor = MaterialTheme.colors.secondary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppBar() {
    Conference2021Theme {
        AppBar(
            appBarState = AppBarState(
                pagerState = PagerState(3),
            ),
            onNavigationIconClick = {},
            onSelectTab = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDarkAppBar() {
    Conference2021Theme(theme = Theme.DARK) {
        AppBar(
            appBarState = AppBarState(
                pagerState = PagerState(3),
            ),
            onNavigationIconClick = {},
            onSelectTab = {}
        )
    }
}
