package io.github.droidkaigi.confsched2021.news

import androidx.compose.foundation.Box
import androidx.compose.foundation.ContentGravity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.launchInComposition
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import io.github.droidkaigi.confsched2021.news.uicomponent.R
import kotlinx.coroutines.delay

private const val SplashWaitTime: Long = 2000

@Composable
fun LandingScreen(modifier: Modifier = Modifier, onTimeout: () -> Unit) {
    Box(modifier = modifier.fillMaxSize(), gravity = ContentGravity.Center) {
        launchInComposition {
            delay(SplashWaitTime)
            onTimeout()
        }
        Image(asset = imageResource(id = R.drawable.ic_logo_big), Modifier.fillMaxSize(0.5F))
    }
}