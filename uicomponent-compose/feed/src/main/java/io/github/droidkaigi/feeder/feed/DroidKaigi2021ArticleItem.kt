package io.github.droidkaigi.feeder.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
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
import io.github.droidkaigi.feeder.Media
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.core.theme.typography
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun DroidKaigi2021ArticleItem(
    onClick: () -> Unit,
    shouldPadding: Boolean,
) {
    ConstraintLayout(
        modifier = Modifier
            .clickable(
                onClick = { onClick() }
            )
            .fillMaxWidth()
            .semantics(mergeDescendants = true) { }
    ) {
        val (image, title, media, date, favorite, favoriteAnim) = createRefs()
        val horizontalPadding = if (shouldPadding) 20.dp else 0.dp
        val verticalPadding = if (shouldPadding) 48.dp else 0.dp
        Image(
            painterResource(id = R.drawable.article_droidkaigi_2021),
            contentDescription = null,
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
        )
        Text(
            modifier = Modifier.constrainAs(title) {
                start.linkTo(parent.start, 20.dp)
                top.linkTo(image.bottom, 20.dp)
                end.linkTo(favorite.start, 20.dp)
                width = Dimension.fillToConstraints
            },
            text = "DroidKaigi 2021 2021/10/19~",
            style = typography.h5,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier
                .constrainAs(media) {
                    top.linkTo(title.bottom, 8.dp)
                    start.linkTo(parent.start, 20.dp)
                    bottom.linkTo(parent.bottom, 12.dp)
                }
                .background(
                    color = Color(0xFFF8572B),
                    shape = MaterialTheme.shapes.small
                )
                .padding(vertical = 4.dp, horizontal = 8.dp),
            text = Media.Conference.text,
            color = Color.White
        )
        CompositionLocalProvider(LocalContentAlpha provides 0.54f) {
            Text(
                modifier = Modifier.constrainAs(date) {
                    top.linkTo(title.bottom, 8.dp)
                    bottom.linkTo(parent.bottom, 12.dp)
                    start.linkTo(media.end, 12.dp)
                },
                text = publishedDateString(Instant.fromEpochMilliseconds(1634482800000)),
                style = typography.caption
            )
        }
    }
}

fun publishedDateString(publishedAt: Instant): String {
    val localDate = publishedAt
        .toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDate.year}/${localDate.monthNumber}/${localDate.dayOfMonth}"
}

@Preview(showBackground = true)
@Composable
fun PreviewDroidKaigi2021ArticleItem() {
    ConferenceAppFeederTheme {
        DroidKaigi2021ArticleItem(
            onClick = { },
            shouldPadding = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDroidKaigi2021ArticleItemWithFiltered() {
    ConferenceAppFeederTheme {
        DroidKaigi2021ArticleItem(
            onClick = { },
            shouldPadding = true
        )
    }
}
