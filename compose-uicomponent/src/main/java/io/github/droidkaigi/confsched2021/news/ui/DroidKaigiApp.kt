package io.github.droidkaigi.confsched2021.news.ui

import androidx.compose.animation.DpPropKey
import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2021.news.ui.theme.Conferenceapp2021newsTheme

@Composable
fun DroidKaigiApp(firstSplashScreenState: SplashState = SplashState.Shown) {
    Conferenceapp2021newsTheme {
        var splashShown by remember {
            mutableStateOf(firstSplashScreenState)
        }
        val transition = transition(splashTransitionDefinition, splashShown)
        Box {
            LandingScreen(
                modifier = Modifier.drawOpacity(transition[splashAlphaKey]),
            ) {
                splashShown = SplashState.Completed
            }
        }
        AppContent(
            modifier = Modifier.drawOpacity(transition[contentAlphaKey]),
        )
    }
}

enum class SplashState { Shown, Completed }

private val splashAlphaKey = FloatPropKey()
private val contentAlphaKey = FloatPropKey()
private val contentTopPaddingKey = DpPropKey()

private val splashTransitionDefinition = transitionDefinition<SplashState> {
    state(SplashState.Shown) {
        this[splashAlphaKey] = 1f
        this[contentAlphaKey] = 0f
        this[contentTopPaddingKey] = 100.dp
    }
    state(SplashState.Completed) {
        this[splashAlphaKey] = 0f
        this[contentAlphaKey] = 1f
        this[contentTopPaddingKey] = 0.dp
    }
    transition {
        splashAlphaKey using tween(
            durationMillis = 100
        )
        contentAlphaKey using tween(
            durationMillis = 300
        )
        contentTopPaddingKey using spring(
            stiffness = Spring.StiffnessLow
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DroidKaigiAppPreview() {
    Conferenceapp2021newsTheme {
        ProvideNewsViewModel(viewModel = fakeNewsViewModel()) {
            DroidKaigiApp()
        }
    }
}
