package io.github.droidkaigi.feeder.timetable2021

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
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
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import io.github.droidkaigi.feeder.DroidKaigi2021Day
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.TimetableItem
import io.github.droidkaigi.feeder.TimetableItemList
import io.github.droidkaigi.feeder.core.theme.AppThemeWithBackground
import io.github.droidkaigi.feeder.core.use

sealed class TimetableTab(val name: String, val routePath: String, val day: DroidKaigi2021Day) {
    object Day1 : TimetableTab("Day1", "day1", DroidKaigi2021Day.Day1)
    object Day2 : TimetableTab("Day2", "day2", DroidKaigi2021Day.Day2)
    object Day3 : TimetableTab("Day3", "day3", DroidKaigi2021Day.Day3)

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
    onDetailClick: (String) -> Unit,
) {
    val (state, effectFlow, dispatch) = use(sessionViewModel())
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val pagerState = rememberPagerState(
        pageCount = TimetableTab.values().size,
        initialPage = TimetableTab.values().indexOf(selectedTab)
    )

    TimetableScreen(
        state = TimetableScreenState(
            timeTableContents = state.filteredTimetableContents,
            scaffoldState = scaffoldState,
            tabPagerState = pagerState
        ),
        onNavigationIconClick = onNavigationIconClick,
        onSelectTab = onSelectedTab,
        onDetailClick = onDetailClick,
        onFavoriteChange = { timetableItem ->
            dispatch(
                TimetableViewModel.Event.ToggleFavorite(timetableItem = timetableItem)
            )
        },
        filters = state.filters,
        onFavoriteFilterChanged = {
            dispatch(
                TimetableViewModel.Event.ChangeFavoriteFilter(
                    filters = state.filters.copy(filterFavorite = it)
                )
            )
        }
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
    onDetailClick: (String) -> Unit,
    onFavoriteChange: (TimetableItem) -> Unit,
    filters: Filters,
    onFavoriteFilterChanged: (filtered: Boolean) -> Unit,
) {
    Conference2021Theme() {
        val density = LocalDensity.current
        BackdropScaffold(
            backLayerBackgroundColor = MaterialTheme.colors.primarySurface,
            scaffoldState = state.scaffoldState,
            backLayerContent = {
                BackLayerContent(filters, onFavoriteFilterChanged)
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
                    TimetableList(
                        state = TimetableListState(
                            timetableItems = state.timeTableContents
                                .timetableItems
                                .getDayTimetableItems(selectedTab.day),
                            favorites = state.timeTableContents.favorites,
                            onDetailClick = onDetailClick,
                            onFavoriteChange = onFavoriteChange,
                        ),
                    )
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

data class TimetableListState(
    val timetableItems: TimetableItemList,
    val onDetailClick: (String) -> Unit,
    val favorites: Set<String>,
    val onFavoriteChange: (TimetableItem) -> Unit,
)

@Composable
private fun TimetableList(
    state: TimetableListState,
) {
    LazyColumn(
        contentPadding = rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars,
            applyStart = false,
            applyTop = false,
            applyEnd = false
        ),
    ) {
        itemsIndexed(
            items = state.timetableItems,
            key = { _, item -> item.id }
        ) { index, timetableItem ->
            TimetableItem(
                timetableItemState = TimetableItemState(
                    timetableItem,
                    state.favorites.contains(timetableItem.id)
                ),
                onDetailClick = state.onDetailClick,
                onFavoriteChange = state.onFavoriteChange,
                showDivider = index > 0
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
                onNavigationIconClick = {},
                onDetailClick = {},
            )
        }
    }
}
