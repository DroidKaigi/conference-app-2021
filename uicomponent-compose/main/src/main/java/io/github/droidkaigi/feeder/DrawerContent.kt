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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.feed.FeedTabs
import io.github.droidkaigi.feeder.main.R
import io.github.droidkaigi.feeder.other.OtherTabs

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
        route = "feed/${FeedTabs.Home.routePath}"
    ),
    BLOG(
        group = Group.NEWS,
        imageResId = R.drawable.ic_baseline_list_alt_24,
        label = "BLOG",
        route = "feed/${FeedTabs.FilteredFeed.Blog.routePath}"
    ),
    VIDEO(
        group = Group.NEWS,
        imageResId = R.drawable.ic_baseline_list_alt_24,
        label = "VIDEO",
        route = "feed/${FeedTabs.FilteredFeed.Video.routePath}"
    ),
    PODCAST(
        group = Group.NEWS,
        imageResId = R.drawable.ic_baseline_list_alt_24,
        label = "PODCAST",
        route = "feed/${FeedTabs.FilteredFeed.Podcast.routePath}"
    ),
    ABOUT_DROIDKAIGI(
        group = Group.OTHER,
        imageResId = R.drawable.ic_baseline_android_24,
        label = "ABOUT",
        route = "other/${OtherTabs.AboutThisApp.routePath}"
    ),
    CONTRIBUTOR(
        group = Group.OTHER,
        imageResId = R.drawable.ic_outline_people_24,
        label = "CONTRIBUTOR",
        route = "other/${OtherTabs.Contributor.routePath}"
    ),
    STAFF(
        group = Group.OTHER,
        imageResId = R.drawable.ic_baseline_face_24,
        label = "STAFF",
        route = "other/${OtherTabs.Staff.routePath}"
    ),
    SETTING(
        group = Group.OTHER,
        imageResId = R.drawable.ic_baseline_settings_24,
        label = "SETTING",
        route = "other/${OtherTabs.Settings.routePath}",
    ),
    ;

    enum class Group {
        NEWS, OTHER;
    }
}

@Composable
fun DrawerContent(
    currentRoute: String = DrawerContents.HOME.route,
    onNavigate: (route: String) -> Unit,
) {
    Column {
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
    onNavigate: (route: String) -> Unit,
) {
    for (content in groupContents) {
        DrawerButton(
            painter = painterResource(id = content.imageResId),
            label = content.label,
            isSelected = content.route == currentRoute,
            {
                onNavigate(content.route)
            }
        )
    }
}

@Preview
@Composable
fun PreviewDrawerContent() {
    ConferenceAppFeederTheme {
        Surface {
            DrawerContent(currentRoute = DrawerContents.HOME.route) {
            }
        }
    }
}
