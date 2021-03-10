package io.github.droidkaigi.feeder.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.imageloading.ImageLoadState

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale,
    contentDescription: String?,
) {
    Box(modifier = modifier.background(Color.Gray))
    CoilImage(
        data = url,
        contentDescription = contentDescription,
        modifier = modifier.testTag("NetworkImage"),
        contentScale = contentScale,
        onRequestCompleted = { result ->
            when (result) {
                is ImageLoadState.Error -> result.throwable.printStackTrace()
                else -> {
                }
            }
        },
        loading = {
            PlaceholderImage()
        },
        error = {
            PlaceholderImage()
        }
    )
}

@Preview
@Composable
fun PreviewNetworkImage() {
    NetworkImage(
        url = "",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale
            .Crop,
        contentDescription = ""
    )
}

@Composable
private fun PlaceholderImage() {
    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.droid_placeholder),
            contentDescription = "Loading…"
        )
    }
}
