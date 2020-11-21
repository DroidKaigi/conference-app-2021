package io.github.droidkaigi.confsched2021.news.ui.article

import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2021.news.ui.newsViewModel
import io.github.droidkaigi.confsched2021.news.uicomponent.R

enum class FilterState(val text: String) {
    All("All"), Favorite("Favorites")
}

@Composable
fun BackLayerContent() {
    val viewModel = newsViewModel()
    val filterState by viewModel.filter.collectAsState()
    val filters = viewModel.filter.value
    Input(text = if (filterState.filterFavorite) "Favorites" else "All") {
        viewModel.onFilterChanged(filters.copy(filterFavorite = !filters.filterFavorite))
    }
    Spacer(Modifier.preferredHeight(8.dp))
}

@Composable
private fun Input(text: String, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .padding(all = 12.dp)
    ) {
        Surface(
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier
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
                        .align(Alignment.CenterVertically),
                    text = text,
                    style = MaterialTheme.typography.body1
                )
                Spacer(Modifier.preferredWidth(8.dp))
            }
        }
    }
}