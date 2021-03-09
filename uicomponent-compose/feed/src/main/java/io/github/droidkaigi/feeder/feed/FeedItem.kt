package io.github.droidkaigi.feeder.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Media
import io.github.droidkaigi.feeder.core.NetworkImage
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.core.theme.typography
import io.github.droidkaigi.feeder.fakeFeedContents

@Composable
fun FeedItem(
    feedItem: FeedItem,
    favorited: Boolean,
    showMediaLabel: Boolean = false,
    onClick: (FeedItem) -> Unit,
    onFavoriteChange: (FeedItem) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .semantics { testTag = "FeedItem" }
            .clickable(
                onClick = { onClick(feedItem) }
            )
            .fillMaxWidth()
            .semantics(mergeDescendants = true) { }
    ) {
        val (media, image, title, date, favorite, speakers) = createRefs()
        if (showMediaLabel) {
            Text(
                modifier = Modifier
                    .constrainAs(media) {
                        top.linkTo(parent.top, 12.dp)
                        start.linkTo(parent.start, 24.dp)
                    }
                    .background(
                        color = feedItem.media.color(),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                text = feedItem.media.text,
                color = Color.White
            )
        }
        NetworkImage(
            url = feedItem.image.standardUrl,
            modifier = Modifier
                .constrainAs(image) {
                    if (showMediaLabel) {
                        top.linkTo(media.bottom, 12.dp)
                    } else {
                        top.linkTo(parent.top, 12.dp)
                    }
                    start.linkTo(parent.start, 24.dp)
                    bottom.linkTo(parent.bottom, 12.dp)
                }
                .width(96.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .aspectRatio(1F / 1F),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Text(
            modifier = Modifier.constrainAs(title) {
                start.linkTo(image.end, 16.dp)
                top.linkTo(image.top)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            },
            text = feedItem.title.currentLangTitle,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        val speakerModifier = Modifier.constrainAs(speakers) {
            start.linkTo(title.start)
            bottom.linkTo(date.top)
            end.linkTo(title.end)
            width = Dimension.fillToConstraints
        }
        if (feedItem is FeedItem.Podcast) {
            SpeakersItem(
                modifier = speakerModifier.padding(top = 8.dp, bottom = 8.dp),
                speakers = feedItem.speakers
            )
        } else {
            Box(speakerModifier)
        }
        CompositionLocalProvider(LocalContentAlpha provides 0.54f) {
            Text(
                modifier = Modifier.constrainAs(date) {
                    bottom.linkTo(parent.bottom, 16.dp)
                    start.linkTo(image.end, 16.dp)
                },
                text = feedItem.publishedDateString(),
                style = typography.caption
            )
        }
        IconToggleButton(
            checked = false,
            modifier = Modifier.constrainAs(favorite) {
                top.linkTo(date.top)
                bottom.linkTo(date.bottom)
                end.linkTo(parent.end, 16.dp)
            },
            content = {
                Icon(
                    painterResource(
                        if (favorited) {
                            R.drawable
                                .ic_baseline_favorite_24
                        } else {
                            R.drawable
                                .ic_baseline_favorite_border_24
                        }
                    ),
                    "favorite"
                )
            },
            onCheckedChange = {
                onFavoriteChange(feedItem)
            }
        )
    }
}

@Composable
private fun Media.color() = when (this) {
    Media.YouTube -> {
        Color.Red
    }
    Media.Medium -> {
        Color.DarkGray
    }
    Media.DroidKaigiFM -> {
        MaterialTheme.colors.secondary
    }
    else -> {
        Color.Gray
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedItem() {
    ConferenceAppFeederTheme {
        val feedItem = fakeFeedContents().feedItemContents[0]
        FeedItem(
            feedItem = feedItem,
            favorited = false,
            showMediaLabel = false,
            onClick = { },
            onFavoriteChange = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedItemWithMedia() {
    ConferenceAppFeederTheme {
        val feedItem = fakeFeedContents().feedItemContents[0]
        FeedItem(
            feedItem = feedItem,
            favorited = false,
            showMediaLabel = true,
            onClick = { },
            onFavoriteChange = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFeedItemWithSpeaker() {
    ConferenceAppFeederTheme {
        val feedItem = fakeFeedContents().feedItemContents.first { it is FeedItem.Podcast }
        FeedItem(
            feedItem = feedItem,
            favorited = false,
            showMediaLabel = true,
            onClick = { },
            onFavoriteChange = { }
        )
    }
}
