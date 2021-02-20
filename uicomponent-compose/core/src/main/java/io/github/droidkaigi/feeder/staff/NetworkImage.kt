package io.github.droidkaigi.feeder.staff

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.imageloading.ImageLoadState

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale,
) {
    Box(modifier = modifier.background(Color.Gray))
    CoilImage(
        data = url,
        contentDescription = "news image",
        modifier = modifier.testTag("NetworkImage"),
        contentScale = contentScale,
        onRequestCompleted = { result ->
            when (result) {
                is ImageLoadState.Error -> result.throwable.printStackTrace()
                else -> {}
            }
        },
        loading = {
        }
    )
}

@Preview
@Composable
fun PreviewNetworkImage() {
    NetworkImage(
        url = "",
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        contentScale = ContentScale
            .Crop
    )
}
