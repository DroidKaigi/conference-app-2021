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
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.coil.rememberCoilPainter

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale,
    contentDescription: String?,
) {
    Box(modifier = modifier.background(Color.Gray))
    Image(
        painter = rememberCoilPainter(
            request = url,
            shouldRefetchOnSizeChange = { _, _ -> false },
            previewPlaceholder = R.drawable.droid_placeholder
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
