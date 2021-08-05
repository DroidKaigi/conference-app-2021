package io.github.droidkaigi.feeder.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale,
    contentDescription: String?,
) {
    Image(
        painter = rememberImagePainter(
            data = url,
            builder = {
                placeholder(R.drawable.droid_placeholder)
            }
        ),
        contentDescription = contentDescription,
        modifier = modifier.testTag("NetworkImage"),
        contentScale = contentScale,
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
