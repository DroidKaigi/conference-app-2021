package io.github.droidkaigi.feeder.timetable2021

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import io.github.droidkaigi.feeder.Theme
import io.github.droidkaigi.feeder.core.theme.shapes

@Composable
fun Conference2021Theme(
    theme: Theme? = Theme.SYSTEM,
    content: @Composable () -> Unit,
) {
    ProvideWindowInsets {
        MaterialTheme(
            colors = colorPalette(theme),
            typography = Typography(),
            shapes = shapes,
            content = content
        )
    }
}

// TODO: Define color codes
private val LightColorPalette = lightColors(
    primary = Color(0xFFFFECE6),
    onPrimary = Color(0xFFF8572B),
    secondary = Color(0xFFF8572B),
    onSecondary = Color.White,
)

private fun colorPalette(theme: Theme?): Colors {
    return when (theme) {
        // TODO: Adapt dark theme
        Theme.SYSTEM -> LightColorPalette
        Theme.DARK -> LightColorPalette
        Theme.LIGHT -> LightColorPalette
        else -> LightColorPalette
    }
}
