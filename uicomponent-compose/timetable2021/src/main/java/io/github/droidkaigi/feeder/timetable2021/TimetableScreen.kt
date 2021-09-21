package io.github.droidkaigi.feeder.timetable2021

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.core.theme.AppThemeWithBackground
import io.github.droidkaigi.feeder.core.use

sealed class TimetableTab(val name: String, val routePath: String) {
    object Day1 : TimetableTab("Day1", "day1")
    object Day2 : TimetableTab("Day1", "day1")
    object Day3 : TimetableTab("Day1", "day1")

    companion object {
        fun values() = listOf(Day1, Day2, Day3)
        fun ofRoutePath(routePath: String) = values().find { it.routePath == routePath } ?: Day1
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Stable
data class TimetableScreenState(
    val timeTableContents: TimetableContents,
    val scaffoldState: BackdropScaffoldState,
    val tabPagerState: PagerState,
)

/**
 * stateful
 */
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun TimetableScreen(
    selectedTab: TimetableTab,
    onSelectedTab: (TimetableTab) -> Unit,
    onNavigationIconClick: () -> Unit,
) {
    val (state, effectFlow, dispatch) = use(sessionViewModel())
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val pagerState = rememberPagerState(
        pageCount = TimetableTab.values().size,
        initialPage = TimetableTab.values().indexOf(selectedTab)
    )

    TimetableScreen(
        state = TimetableScreenState(
            timeTableContents = state.timetableContents,
            scaffoldState = scaffoldState,
            tabPagerState = pagerState
        ),
        onNavigationIconClick = onNavigationIconClick,
        onSelectTab = onSelectedTab
    )
}

/**
 * Stateless
 */
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
private fun TimetableScreen(
    state: TimetableScreenState,
    onNavigationIconClick: () -> Unit,
    onSelectTab: (TimetableTab) -> Unit,
) {
    Conference2021Theme() {
        val density = LocalDensity.current
        BackdropScaffold(
            backLayerBackgroundColor = MaterialTheme.colors.primarySurface,
            scaffoldState = state.scaffoldState,
            backLayerContent = {
                // TODO
                Text(text = "Implement me!!!!")
            },
            frontLayerShape = MaterialTheme.shapes.large,
            peekHeight = 104.dp + (LocalWindowInsets.current.systemBars.top / density.density).dp,
            appBar = {
                AppBar(
                    appBarState = AppBarState(
                        pagerState = state.tabPagerState,
                    ),
                    onNavigationIconClick = onNavigationIconClick,
                    onSelectTab = onSelectTab
                )
            },
            frontLayerContent = {
                HorizontalPager(
                    state = state.tabPagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    val selectedTab = TimetableTab.values()[page]
                    Column {
                        state.timeTableContents.timetableItems.forEach {
                            Text(it.title.currentLangTitle)
                        }
                    }
                }
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = it,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        )
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
