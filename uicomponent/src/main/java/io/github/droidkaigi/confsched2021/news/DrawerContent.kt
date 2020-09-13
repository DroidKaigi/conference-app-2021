package io.github.droidkaigi.confsched2021.news

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.vectorResource
import io.github.droidkaigi.confsched2021.news.uicomponent.R

@Composable
fun DrawerContent() {
    DrawerButton(
        icon = vectorResource(id = R.drawable.ic_baseline_list_alt_24),
        label = "Articles",
        isSelected = true,
        {

        }
    )
    DrawerButton(
        icon = vectorResource(id = R.drawable.ic_baseline_info_24),
        label = "About this app",
        isSelected = false,
        {

        }
    )
}