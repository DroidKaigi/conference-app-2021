package io.github.droidkaigi.confsched2021.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import io.github.droidkaigi.confsched2021.news.uicomponent.R

@Composable
fun DrawerContent(navController: NavHostController) {
    DrawerButton(
        icon = vectorResource(id = R.drawable.ic_baseline_list_alt_24),
        label = "NewsContents",
        isSelected = true,
        {
            navController.navigate("newsContents")
        }
    )
    DrawerButton(
        icon = vectorResource(id = R.drawable.ic_baseline_info_24),
        label = "About this app",
        isSelected = false,
        {
            navController.navigate("about_this_app")
        }
    )
}
