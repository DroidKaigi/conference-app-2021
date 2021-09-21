package io.github.droidkaigi.feeder

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MicNone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.TextSnippet
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.outlined.People
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import io.github.droidkaigi.feeder.core.theme.AppThemeWithBackground
import io.github.droidkaigi.feeder.feed.FeedTab
import io.github.droidkaigi.feeder.main.R
import io.github.droidkaigi.feeder.other.OtherTab
import io.github.droidkaigi.feeder.timetable2021.TimetableTab

enum class DrawerContents(
    val group: Group,
    val imageVector: ImageVector,
    val label: String,
    val route: String,
) {
    HOME(
        group = Group.NEWS,
        imageVector = Icons.Filled.Home,
        label = "HOME",
        route = "feed/${FeedTab.Home.routePath}"
    ),
    BLOG(
        group = Group.NEWS,
        imageVector = Icons.Filled.TextSnippet,
        label = "BLOG",
        route = "feed/${FeedTab.FilteredFeed.Blog.routePath}"
    ),
    VIDEO(
        group = Group.NEWS,
        imageVector = Icons.Filled.Videocam,
        label = "VIDEO",
        route = "feed/${FeedTab.FilteredFeed.Video.routePath}"
    ),
    PODCAST(
        group = Group.NEWS,
        imageVector = Icons.Filled.MicNone,
        label = "PODCAST",
        route = "feed/${FeedTab.FilteredFeed.Podcast.routePath}"
    ),
    TIMETABLE(
        group = Group.TIMETABLE2021,
        imageVector = Icons.Filled.AccessTime,
        label = "TIME TABLE",
        route = "timetable/${OtherTab.AboutThisApp.routePath}"
    ),
    ABOUT_DROIDKAIGI(
        group = Group.OTHER,
        imageVector = Icons.Filled.Android,
        label = "ABOUT",
        route = "other/${OtherTab.AboutThisApp.routePath}"
    ),
    CONTRIBUTOR(
        group = Group.OTHER,
        imageVector = Icons.Outlined.People,
        label = "CONTRIBUTOR",
        route = "other/${OtherTab.Contributor.routePath}"
    ),
    STAFF(
        group = Group.OTHER,
        imageVector = Icons.Filled.Face,
        label = "STAFF",
        route = "other/${OtherTab.Staff.routePath}"
    ),
    SETTING(
        group = Group.OTHER,
        imageVector = Icons.Filled.Settings,
        label = "SETTING",
        route = "other/${OtherTab.Settings.routePath}",
    ),
    ;

    enum class Group {
        NEWS, TIMETABLE2021, OTHER;
    }
}

class DrawerContentState(
    initialValue: String,
) {
    var currentValue: String by mutableStateOf(initialValue)
        private set

    fun selectDrawerContent(route: String): Boolean {
        return if (currentValue != route) {
            currentValue = route
            true
        } else {
            false
        }
    }

    fun onSelectDrawerContent(feedTab: FeedTab) {
        selectDrawerContent("feed/${feedTab.routePath}")
    }

    fun onSelectDrawerContent(timetableTab: TimetableTab) {
        selectDrawerContent("timetable/${timetableTab.routePath}")
    }

    fun onSelectDrawerContent(otherTab: OtherTab) {
        selectDrawerContent("other/${otherTab.routePath}")
    }

    companion object {

        fun Saver() = Saver<DrawerContentState, String>(
            save = { it.currentValue },
            restore = { DrawerContentState(it) }
        )
    }
}

@Composable
fun rememberDrawerContentState(initialValue: String): DrawerContentState {
    return rememberSaveable(saver = DrawerContentState.Saver()) {
        DrawerContentState(initialValue)
    }
}

@Composable
fun DrawerContent(
    currentRoute: String = DrawerContents.HOME.route,
    onNavigate: (contents: DrawerContents) -> Unit,
) {
    LazyColumn(modifier = Modifier.navigationBarsPadding()) {
        item {
            Spacer(modifier = Modifier.height(52.dp))
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "logo"
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = "DroidKaigi",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider()
            Spacer(modifier = Modifier.height(12.dp))
        }
        items(DrawerContents.Group.values()) { group ->
            when (group) {
                DrawerContents.Group.NEWS -> {
                    val newsContents = DrawerContents.values()
                        .filter { content -> content.group == DrawerContents.Group.NEWS }
                    DrawerContentGroup(newsContents, currentRoute, onNavigate)
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                }
                DrawerContents.Group.TIMETABLE2021 -> {
                    val newsContents = DrawerContents.values()
                        .filter { content ->
                            content.group == DrawerContents.Group.TIMETABLE2021
                        }
                    DrawerContentGroup(newsContents, currentRoute, onNavigate)
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                }
                DrawerContents.Group.OTHER -> {
                    val otherContents = DrawerContents.values()
                        .filter { content -> content.group == DrawerContents.Group.OTHER }
                    DrawerContentGroup(otherContents, currentRoute, onNavigate)
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Divider()
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 17.dp, bottom = 26.dp)
            ) {
                Image(
                    modifier = Modifier.clickable {
                        onNavigate(DrawerContents.TIMETABLE)
                    },
                    painter = painterResource(id = R.drawable.banner_droidkaigi_2021),
                    contentDescription = stringResource(id = R.string.banner_droidkaigi_2021)
                )
            }
        }
    }
}

@Composable
private fun DrawerContentGroup(
    groupContents: List<DrawerContents>,
    currentRoute: String,
    onNavigate: (contents: DrawerContents) -> Unit,
) {

    for (content in groupContents) {
        DrawerButton(
            imageVector = content.imageVector,
            label = content.label,
            isSelected = content.route == currentRoute,
            {
                onNavigate(content)
            }
        )
    }
}

@Preview
@Composable
fun PreviewDrawerContent() {
    AppThemeWithBackground {
        DrawerContent(currentRoute = DrawerContents.HOME.route) {
        }
    }
}
