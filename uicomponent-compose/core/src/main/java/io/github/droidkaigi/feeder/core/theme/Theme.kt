package io.github.droidkaigi.feeder.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

private val DarkColorPalette = darkColors(
    primary = blue200,
    primaryVariant = blue700,
    secondary = green200,
)

private val LightColorPalette = lightColors(
    primary = blue200,
    primaryVariant = blue700,
    secondary = green200,
)

@Composable
fun ConferenceAppFeederTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable
    () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    ProvideWindowInsets {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

@Composable
fun AppThemeWithBackground(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable
    () -> Unit,
) {
    Surface {
        ConferenceAppFeederTheme(darkTheme, content)
    }
}
