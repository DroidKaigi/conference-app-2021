package io.github.droidkaigi.confsched2021.news.ui

import androidx.annotation.IdRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2021.news.ui.news.NewsTabs
import io.github.droidkaigi.confsched2021.news.ui.theme.Conferenceapp2021newsTheme
import io.github.droidkaigi.confsched2021.news.uicomponent.main.R

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
        route = "news/${NewsTabs.Home.routePath}"
    ),
    BLOG(
        group = Group.NEWS,
        imageResId = R.drawable.ic_baseline_list_alt_24,
        label = "BLOG",
        route = "news/${NewsTabs.FilteredNews.Blog.routePath}"
    ),
    VIDEO(
        group = Group.NEWS,
        imageResId = R.drawable.ic_baseline_list_alt_24,
        label = "VIDEO",
        route = "news/${NewsTabs.FilteredNews.Video.routePath}"
    ),
    PODCAST(
        group = Group.NEWS,
        imageResId = R.drawable.ic_baseline_list_alt_24,
        label = "PODCAST",
        route = "news/${NewsTabs.FilteredNews.Podcast.routePath}"
    ),
    ABOUT_DROIDKAIGI(
        group = Group.OTHER,
        imageResId = R.drawable.ic_baseline_list_alt_24,
        label = "DroidKaigiとは",
        route = "news/about"
    ),
    CONTRIBUTOR(
        group = Group.OTHER,
        imageResId = R.drawable.ic_baseline_list_alt_24,
        label = "CONTRIBUTOR",
        route = "other/contributor"
    ),
    STAFF(
        group = Group.OTHER,
        imageResId = R.drawable.ic_baseline_list_alt_24,
        label = "STAFF",
        route = "other/staff"
    ),
    SETTING(
        group = Group.OTHER,
        imageResId = R.drawable.ic_baseline_list_alt_24,
        label = "SETTING",
        route = "other/settings"
    ),
    ;

    enum class Group {
        NEWS, OTHER
    }
}

@Composable
fun DrawerContent(onNavigate: (route: String) -> Unit) {
    Column {
        Spacer(modifier = Modifier.height(36.dp))
        for (group in DrawerContents.Group.values()) {
            val groupContents = DrawerContents.values()
                .filter { content -> content.group == group }
            DrawerContentGroup(groupContents, onNavigate)
            Divider()
        }
    }
}

@Composable
private fun DrawerContentGroup(
    groupContents: List<DrawerContents>,
    onNavigate: (route: String) -> Unit,
) {
    for (content in groupContents) {
        DrawerButton(
            icon = vectorResource(id = content.imageResId),
            label = content.label,
            isSelected = true,
            {
                onNavigate(content.route)
            }
        )
    }
}

@Preview()
@Composable
fun PreviewDrawerContent() {
    Conferenceapp2021newsTheme {
        DrawerContent {

        }
    }
}
