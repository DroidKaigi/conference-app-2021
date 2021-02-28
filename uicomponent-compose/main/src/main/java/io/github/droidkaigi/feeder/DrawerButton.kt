package io.github.droidkaigi.feeder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.main.R

// from JetNews

@Composable
fun DrawerButton(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    action: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colors
    val imageAlpha = if (isSelected) {
        1f
    } else {
        0.6f
    }
    val iconColor = if (isSelected) {
        colors.secondary
    } else {
        colors.onSurface.copy(alpha = 0.6f)
    }
    val textColor = if (isSelected) {
        colors.onSurface
    } else {
        colors.onSurface.copy(alpha = 0.4f)
    }

    val surfaceModifier = modifier
        .padding(start = 8.dp, top = 8.dp, end = 8.dp)
        .fillMaxWidth()
    Surface(
        modifier = surfaceModifier,
        shape = MaterialTheme.shapes.small
    ) {
        TextButton(
            onClick = action,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    imageVector = icon,
                    contentDescription = label,
                    colorFilter = ColorFilter.tint(iconColor),
                    alpha = imageAlpha,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(Modifier.width(32.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.subtitle2,
                    color = textColor,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewDrawerButton() {
    ConferenceAppFeederTheme {
        DrawerButton(
            icon = ImageVector.vectorResource(id = R.drawable.ic_baseline_home_24),
            label = "HOME",
            isSelected = true,
            action = {}
        )
    }
}
