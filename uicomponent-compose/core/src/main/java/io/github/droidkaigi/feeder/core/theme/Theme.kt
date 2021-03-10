package io.github.droidkaigi.feeder.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import io.github.droidkaigi.feeder.Theme

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
    theme: Theme? = Theme.SYSTEM,
    content: @Composable
    () -> Unit,
) {
    ProvideWindowInsets {
        MaterialTheme(
            colors = colorPalette(theme),
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

@Composable
private fun colorPalette(theme: Theme?): Colors {
    return when (theme) {
        Theme.SYSTEM -> systemColorPalette()
        Theme.BATTERY -> DarkColorPalette
        Theme.DARK -> DarkColorPalette
        Theme.LIGHT -> LightColorPalette
        else -> throw IllegalArgumentException("should not happen")
    }
}

@Composable
private fun systemColorPalette(): Colors {
    return if (isSystemInDarkTheme()) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
}

@Composable
fun AppThemeWithBackground(
    darkTheme: Theme? = Theme.SYSTEM,
    content: @Composable
    () -> Unit,
) {
    Surface {
        ConferenceAppFeederTheme(darkTheme, content)
    }
}
