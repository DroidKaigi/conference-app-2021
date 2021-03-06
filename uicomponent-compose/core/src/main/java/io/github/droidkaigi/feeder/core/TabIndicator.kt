package io.github.droidkaigi.feeder.core.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TabIndicator(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .background(
                color = MaterialTheme.colors.secondary,
                shape = MaterialTheme.shapes.small,
            )
    )
}
