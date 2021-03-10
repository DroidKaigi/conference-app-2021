package io.github.droidkaigi.feeder

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.core.use

@Composable
fun DroidKaigiApp(firstSplashScreenState: SplashState = SplashState.Shown) {
    ProvideDroidKaigiAppViewModel(viewModel = droidKaigiAppViewModel()) {
        val (state) = use(droidKaigiAppViewModel())

        ConferenceAppFeederTheme(state.theme) {
            var splashShown by rememberSaveable {
                mutableStateOf(firstSplashScreenState)
            }
            val transition = updateTransition(splashShown)
            val splashAlpha: Float by transition.animateFloat(
                transitionSpec = { tween(durationMillis = 100) }
            ) { state ->
                if (state == SplashState.Shown) 1f else 0f
            }
            val contentAlpha: Float by transition.animateFloat(
                transitionSpec = { tween(durationMillis = 300) }
            ) { state ->
                if (state == SplashState.Shown) 0f else 1f
            }

            Box {
                LandingScreen(
                    modifier = Modifier.alpha(splashAlpha),
                ) {
                    splashShown = SplashState.Completed
                }
            }
            AppContent(
                modifier = Modifier
                    .alpha(contentAlpha)
                    .navigationBarsPadding(bottom = false)
            )
        }
    }
}

enum class SplashState { Shown, Completed }
