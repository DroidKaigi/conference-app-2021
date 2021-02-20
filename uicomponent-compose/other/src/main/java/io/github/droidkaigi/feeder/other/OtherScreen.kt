package io.github.droidkaigi.feeder.other

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropScaffoldState
import androidx.compose.material.BackdropValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.AmbientWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import io.github.droidkaigi.feeder.staff.StaffList
import io.github.droidkaigi.feeder.staff.theme.Conferenceapp2021newsTheme
import io.github.droidkaigi.feeder.uicomponent.other.R

sealed class OtherTabs(val name: String, val routePath: String) {
    object AboutThisApp : OtherTabs("About", "about")
    object Contributor : OtherTabs("Contributor", "contributor")
    object Staff : OtherTabs("Staff", "about_this_app")
    object Settings : OtherTabs("Setting", "about_this_app")

    companion object {
        fun values() = listOf(AboutThisApp, Contributor, Staff, Settings)
        fun ofRoutePath(routePath: String) = values().first { it.routePath == routePath }
    }
}

/**
 * stateful
 */
@Composable
fun OtherScreen(
    initialSelectedTab: OtherTabs,
    onNavigationIconClick: () -> Unit,
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    var selectedTab by remember(initialSelectedTab) {
        mutableStateOf(initialSelectedTab)
    }
    OtherScreen(
        scaffoldState = scaffoldState,
        selectedTab = selectedTab,
        onSelectTab = { tab: OtherTabs ->
            selectedTab = tab
        },
        onNavigationIconClick = onNavigationIconClick,
    )
}

@Composable
fun OtherScreen(
    scaffoldState: BackdropScaffoldState,
    selectedTab: OtherTabs,
    onSelectTab: (OtherTabs) -> Unit,
    onNavigationIconClick: () -> Unit,
) {
    Column {
        val density = AmbientDensity.current
        BackdropScaffold(
            backLayerBackgroundColor = MaterialTheme.colors.primary,
            scaffoldState = scaffoldState,
            backLayerContent = {
                Box(modifier = Modifier.height(1.dp))
            },
            frontLayerShape = CutCornerShape(topStart = 32.dp),
            peekHeight = 104.dp + (AmbientWindowInsets.current.systemBars.top / density.density).dp,
            appBar = {
                AppBar(onNavigationIconClick, selectedTab, onSelectTab)
            },
            frontLayerContent = {
                Surface(
                    color = MaterialTheme.colors.background,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    when (selectedTab) {
                        OtherTabs.Staff -> StaffList()
                        else -> {
                            val context = LocalContext.current
                            Text(
                                text = "Not implemented yet. Please create this screen!",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 32.dp)
                                    .clickable {
                                        context.startActivity(
                                            Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse("https://github.com/DroidKaigi/conference-app-2021/issues?q=is%3Aissue+is%3Aopen+label%3Awelcome_contribute")
                                            )
                                        )
                                    }
                            )
                        }
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
        title = { Text("DroidKaigi") },
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(vectorResource(R.drawable.ic_baseline_menu_24), "menu")
            }
        }
    )
    TabRow(
        selectedTabIndex = 0,
        indicator = {
        },
        divider = {}
    ) {
        OtherTabs.values().forEach { tab ->
            Tab(
                selected = tab == selectedTab,
                text = {
                    Text(
                        modifier = if (selectedTab == tab) {
                            Modifier
                                .background(
                                    color = MaterialTheme.colors.secondary,
                                    shape = CutCornerShape(
                                        topStart = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                )
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        } else {
                            Modifier
                        },
                        text = tab.name
                    )
                },
                onClick = { onSelectTab(tab) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewOtherScreen() {
    Conferenceapp2021newsTheme {
        OtherScreen(
            initialSelectedTab = OtherTabs.AboutThisApp,
            onNavigationIconClick = {
            }
        )
    }
}
