package io.github.droidkaigi.feeder.timetable2021

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import io.github.droidkaigi.feeder.core.R as CoreR
import io.github.droidkaigi.feeder.core.TabIndicator
import io.github.droidkaigi.feeder.core.theme.AppThemeWithBackground
import io.github.droidkaigi.feeder.core.use
import kotlinx.coroutines.launch

sealed class TimetableTab(val name: String, val routePath: String) {
    object Day1 : TimetableTab("Day1", "day1")

    companion object {
        fun values() = listOf(Day1)
        fun ofRoutePath(routePath: String) = values().find { it.routePath == routePath } ?: Day1
    }
}

/**
 * stateful
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun TimetableScreen(
    selectedTab: TimetableTab,
    onSelectedTab: (TimetableTab) -> Unit,
    onNavigationIconClick: () -> Unit,
) {
    val (state, effectFlow, dispatch) = use(sessionViewModel())
    Text(text = state.timetableContents.toString())
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun AppBar(
    onNavigationIconClick: () -> Unit,
    pagerState: PagerState,
    tabLazyListStates: Map<TimetableTab, LazyListState>,
    onSelectTab: (TimetableTab) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = { Image(painterResource(CoreR.drawable.toolbar_droidkaigi_logo), "DroidKaigi") },
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
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        },
        divider = {}
    ) {
        TimetableTab.values().forEachIndexed { index, tab ->
            Tab(
                selected = index == pagerState.currentPage,
                text = {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        text = tab.name,
                    )
                },
                onClick = {
                    onSelectTab(tab)
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                        tabLazyListStates.getValue(tab).animateScrollToItem(index = 0)
                    }
                },
                // For tabs to draw in front of indicators
                modifier = Modifier.zIndex(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimetableScreen() {
    AppThemeWithBackground {
        CompositionLocalProvider(
            provideTimetableViewModelFactory { fakeTimetableViewModel() },
        ) {
            TimetableScreen(
                selectedTab = TimetableTab.Day1,
                onSelectedTab = {},
                onNavigationIconClick = {
                }
            )
        }
    }
}
