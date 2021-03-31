package io.github.droidkaigi.feeder.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
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

private val DarkFilterMuskColor = gray
private val LightFilterMuskColor = blue300

@Composable
fun ConferenceAppFeederTheme(
    theme: Theme? = Theme.SYSTEM,
    content: @Composable
    () -> Unit,
) {
    val filterMuskColor = filterMuskColor(theme = theme)
    ProvideWindowInsets {
        CompositionLocalProvider(LocalFilterMuskColor provides filterMuskColor) {
            MaterialTheme(
                colors = colorPalette(theme),
                typography = typography,
                shapes = shapes,
                content = content
            )
        }
    }
}

@Composable
private fun colorPalette(theme: Theme?): Colors {
    return when (theme) {
        Theme.SYSTEM -> systemColorPalette()
        Theme.DARK -> DarkColorPalette
        Theme.LIGHT -> LightColorPalette
        else -> systemColorPalette()
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
private fun filterMuskColor(theme: Theme?): Color {
    return when (theme) {
        Theme.SYSTEM -> systemFilterMuskColor()
        Theme.DARK -> DarkFilterMuskColor
        Theme.LIGHT -> LightFilterMuskColor
        else -> systemFilterMuskColor()
    }
}

@Composable
private fun systemFilterMuskColor(): Color {
    return if (isSystemInDarkTheme()) {
        DarkFilterMuskColor
    } else {
        LightFilterMuskColor
    }
}

@Composable
fun AppThemeWithBackground(
    theme: Theme? = Theme.SYSTEM,
    content: @Composable
    () -> Unit,
) {
    Surface {
        ConferenceAppFeederTheme(theme, content)
    }
}

object ConferenceAppFeederTheme {
    val filterMuskColor: Color
        @Composable
        get() = LocalFilterMuskColor.current
}
