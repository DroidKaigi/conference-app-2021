package io.github.droidkaigi.feeder

import androidx.annotation.IdRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.feeder.core.theme.AppThemeWithBackground
import io.github.droidkaigi.feeder.feed.FeedTab
import io.github.droidkaigi.feeder.main.R
import io.github.droidkaigi.feeder.other.OtherTab

enum class DrawerContents(
    val group: Group,
    @IdRes
    val imageResId: Int,
    val label: String,
    val route: String,
) {
    HOME(
        group = Group.NEWS,
        imageResId = R.drawable.ic_baseline_home_24,
        label = "HOME",
        route = "feed/${FeedTab.Home.routePath}"
    ),
    BLOG(
        group = Group.NEWS,
        imageResId = R.drawable.ic_baseline_text_snippet_24,
        label = "BLOG",
        route = "feed/${FeedTab.FilteredFeed.Blog.routePath}"
    ),
    VIDEO(
        group = Group.NEWS,
        imageResId = R.drawable.ic_baseline_videocam_24,
        label = "VIDEO",
        route = "feed/${FeedTab.FilteredFeed.Video.routePath}"
    ),
    PODCAST(
        group = Group.NEWS,
        imageResId = R.drawable.ic_baseline_mic_none_24,
        label = "PODCAST",
        route = "feed/${FeedTab.FilteredFeed.Podcast.routePath}"
    ),
    ABOUT_DROIDKAIGI(
        group = Group.OTHER,
        imageResId = R.drawable.ic_baseline_android_24,
        label = "ABOUT",
        route = "other/${OtherTab.AboutThisApp.routePath}"
    ),
    CONTRIBUTOR(
        group = Group.OTHER,
        imageResId = R.drawable.ic_outline_people_24,
        label = "CONTRIBUTOR",
        route = "other/${OtherTab.Contributor.routePath}"
    ),
    STAFF(
        group = Group.OTHER,
        imageResId = R.drawable.ic_baseline_face_24,
        label = "STAFF",
        route = "other/${OtherTab.Staff.routePath}"
    ),
    SETTING(
        group = Group.OTHER,
        imageResId = R.drawable.ic_baseline_settings_24,
        label = "SETTING",
        route = "other/${OtherTab.Settings.routePath}",
    ),
    ;

    enum class Group {
        NEWS, OTHER;
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
    Column {
        Spacer(modifier = Modifier.height(20.dp))
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
        LazyColumn {
            items(DrawerContents.Group.values()) { group ->
                when (group) {
                    DrawerContents.Group.NEWS -> {
                        val newsContents = DrawerContents.values()
                            .filter { content -> content.group == DrawerContents.Group.NEWS }
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
            painter = painterResource(id = content.imageResId),
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
