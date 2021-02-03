package io.github.droidkaigi.confsched2021.news.ui.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2021.news.Media
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.fakeNewsContents
import io.github.droidkaigi.confsched2021.news.ui.NetworkImage
import io.github.droidkaigi.confsched2021.news.ui.theme.Conferenceapp2021newsTheme
import io.github.droidkaigi.confsched2021.news.ui.theme.typography
import io.github.droidkaigi.confsched2021.news.uicomponent.news.R

@Composable
fun NewsItem(
    news: News,
    favorited: Boolean,
    showMediaLabel: Boolean = false,
    onClick: (News) -> Unit,
    onFavoriteChange: (News) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .clickable(
                onClick = { onClick(news) }
            )
            .fillMaxWidth()
    ) {
        val (media, image, title, date, favorite) = createRefs()
        if (showMediaLabel) {
            Text(
                modifier = Modifier
                    .constrainAs(media) {
                        top.linkTo(parent.top, 12.dp)
                        start.linkTo(parent.start, 24.dp)
                    }
                    .background(
                        color = news.media.color(),
                        shape = CutCornerShape(
                            topLeft = 8.dp,
                            bottomRight = 8.dp
                        )
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                text = news.media.text,
                color = Color.White
            )
        }
        NetworkImage(
            url = news.image.standardUrl,
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
                .width(96.dp)
//                .aspectRatio(16F / 9F)
                .aspectRatio(1F / 1F),
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.constrainAs(title) {
                start.linkTo(image.end, 16.dp)
                top.linkTo(image.top)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            },
            text = news.title,
            style = typography.h5,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.constrainAs(date) {
                bottom.linkTo(parent.bottom, 16.dp)
                start.linkTo(image.end, 16.dp)
            },
            text = news.publishedDateString()
        )
        IconToggleButton(
            checked = false,
            modifier = Modifier.constrainAs(favorite) {
                bottom.linkTo(image.bottom)
                end.linkTo(parent.end, 16.dp)
            },
            content = {
                Icon(
                    vectorResource(
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
                onFavoriteChange(news)
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
fun PreviewNewsItem() {
    Conferenceapp2021newsTheme {
        val news = fakeNewsContents().newsContents[0]
        NewsItem(
            news = news,
            favorited = false,
            showMediaLabel = false,
            onClick = { },
            onFavoriteChange = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsItemWithMedia() {
    Conferenceapp2021newsTheme {
        val news = fakeNewsContents().newsContents[0]
        NewsItem(
            news = news,
            favorited = false,
            showMediaLabel = true,
            onClick = { },
            onFavoriteChange = { }
        )
    }
}
