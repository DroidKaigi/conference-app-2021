package io.github.droidkaigi.feeder.feed

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Speaker
import io.github.droidkaigi.feeder.core.NetworkImage
import io.github.droidkaigi.feeder.fakeFeedContents

@Composable
fun SpeakersItem(
    modifier: Modifier,
    speakers: List<Speaker>,
) {
    Box(modifier = modifier) {
        speakers.forEachIndexed {i, speaker ->
            NetworkImage(
                url = speaker.iconUrl,
                modifier = Modifier
                    .offset((14 * i).dp)
                    .zIndex((-1 * i).toFloat())
                    .size(18.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun PreviewSpeakersItem() {
    SpeakersItem(
        modifier = Modifier.fillMaxWidth(),
        speakers = (
            fakeFeedContents()
                .feedItemContents
                .first { it is FeedItem.Podcast } as FeedItem.Podcast
            ).speakers
    )
}
