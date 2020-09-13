package io.github.droidkaigi.confsched2021.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2021.news.uicomponent.R

enum class FilterState(val text: String) {
    All("All"), Favorite("Favorites")
}

@Composable
fun BackLayerContent() {
    var filterState by remember { mutableStateOf(FilterState.All) }
    Input(text = filterState.text) {
        filterState = when (filterState) {
            FilterState.All -> FilterState.Favorite
            FilterState.Favorite -> FilterState.All
        }
    }
    Spacer(Modifier.preferredHeight(8.dp))
}

@Composable
private fun Input(text: String, onClick: () -> Unit) {
    Surface(
        color = MaterialTheme.colors.primaryVariant,
        modifier = Modifier
            .padding(all = 12.dp)
            .clickable(onClick = onClick)
            .fillMaxWidth()
    ) {
        Row(
            Modifier.padding(all = 12.dp)
        ) {
            Image(vectorResource(R.drawable.ic_baseline_favorite_24))
            Spacer(Modifier.preferredWidth(8.dp))
            Text(
                modifier = Modifier
                    .gravity(Alignment.CenterVertically),
                text = text,
                style = MaterialTheme.typography.body1
            )
            Spacer(Modifier.preferredWidth(8.dp))
        }
    }
}