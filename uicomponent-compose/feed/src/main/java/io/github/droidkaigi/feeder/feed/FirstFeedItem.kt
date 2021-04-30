package io.github.droidkaigi.feeder.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import io.github.droidkaigi.feeder.FeedItem
import io.github.droidkaigi.feeder.Media
import io.github.droidkaigi.feeder.core.NetworkImage
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.core.theme.typography
import io.github.droidkaigi.feeder.fakeFeedContents

@Composable
fun FirstFeedItem(
    feedItem: FeedItem,
    favorited: Boolean,
    showMediaLabel: Boolean = false,
    onClick: (FeedItem) -> Unit,
    onFavoriteChange: (FeedItem) -> Unit,
    shouldPadding: Boolean
) {
    ConstraintLayout(
        modifier = Modifier
            .clickable(
                onClick = { onClick(feedItem) }
            )
            .fillMaxWidth()
            .semantics(mergeDescendants = true) { }
    ) {
        val (image, title, media, date, favorite, favoriteAnim) = createRefs()
        val horizontalPadding = if (shouldPadding) 20.dp else 0.dp
        val verticalPadding = if (shouldPadding) 48.dp else 0.dp
        NetworkImage(
            url = feedItem.image.standardUrl,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(
                    start = horizontalPadding,
                    end = horizontalPadding,
                    top = verticalPadding
                )
                .wrapContentWidth()
                .aspectRatio(16F / 9F),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Text(
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start, 20.dp)
                top.linkTo(image.bottom, 20.dp)
                end.linkTo(favorite.start, 20.dp)
                width = Dimension.fillToConstraints
            },
            text = feedItem.title.jaTitle,
            style = typography.h5,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        if (showMediaLabel) {
            Text(
                modifier = Modifier
                    .constrainAs(media) {
                        top.linkTo(title.bottom, 8.dp)
                        start.linkTo(parent.start, 20.dp)
                        bottom.linkTo(parent.bottom, 12.dp)
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
        CompositionLocalProvider(LocalContentAlpha provides 0.54f) {
            Text(
                modifier = Modifier.constrainAs(date) {
                    top.linkTo(title.bottom, 8.dp)
                    bottom.linkTo(parent.bottom, 12.dp)
                    if (showMediaLabel) {
                        start.linkTo(media.end, 12.dp)
                    } else {
                        start.linkTo(parent.start, 20.dp)
                    }
                },
                text = feedItem.publishedDateString(),
                style = typography.caption
            )
        }
        FavoriteAnimation(
            visible = favorited,
            modifier = Modifier
                .constrainAs(favoriteAnim) {
                    width = Dimension.value(80.dp)
                    height = Dimension.value(80.dp)
                    start.linkTo(favorite.start)
                    end.linkTo(favorite.end)
                    bottom.linkTo(favorite.bottom, 12.dp)
                }
        )
        IconToggleButton(
            checked = false,
            modifier = Modifier.constrainAs(favorite) {
                top.linkTo(image.bottom, 36.dp)
                end.linkTo(parent.end, 20.dp)
            },
            content = {
                if (favorited) {
                    Icon(
                        painterResource(R.drawable.ic_baseline_favorite_24),
                        "favorite",
                        tint = Color.Red
                    )
                } else {
                    Icon(
                        painterResource(R.drawable.ic_baseline_favorite_border_24),
                        "favorite"
                    )
                }
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
fun PreviewFirstFeedItem() {
    ConferenceAppFeederTheme {
        val feedItem = fakeFeedContents().feedItemContents[0]
        FirstFeedItem(
            feedItem = feedItem,
            favorited = false,
            showMediaLabel = false,
            onClick = { },
            onFavoriteChange = { },
            shouldPadding = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFirstFeedItemWithMedia() {
    ConferenceAppFeederTheme {
        val feedItem = fakeFeedContents().feedItemContents[0]
        FirstFeedItem(
            feedItem = feedItem,
            favorited = false,
            showMediaLabel = true,
            onClick = { },
            onFavoriteChange = { },
            shouldPadding = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFirstFeedItemWithFiltered() {
    ConferenceAppFeederTheme {
        val feedItem = fakeFeedContents().feedItemContents[0]
        FirstFeedItem(
            feedItem = feedItem,
            favorited = false,
            showMediaLabel = true,
            onClick = { },
            onFavoriteChange = { },
            shouldPadding = true
        )
    }
}
