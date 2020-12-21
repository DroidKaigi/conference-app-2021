package io.github.droidkaigi.confsched2021.news.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soywiz.klock.DateTimeTz
import io.github.droidkaigi.confsched2021.news.Image
import io.github.droidkaigi.confsched2021.news.Locale
import io.github.droidkaigi.confsched2021.news.LocaledContents
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.ui.NetworkImage
import io.github.droidkaigi.confsched2021.news.ui.theme.Conferenceapp2021newsTheme
import io.github.droidkaigi.confsched2021.news.ui.theme.typography
import io.github.droidkaigi.confsched2021.news.uicomponent.R

@Composable
fun NewsItem(
    news: News,
    favorited: Boolean,
    onClick: (News) -> Unit,
    onFavoriteChange: (News) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier.clickable(
            onClick = { onClick(news) }
        )
            .fillMaxWidth()
    ) {
        val (source, image, title, date, favorite) = createRefs()
        Text(
            modifier = Modifier.constrainAs(source) {
                top.linkTo(parent.top, 12.dp)
                start.linkTo(parent.start, 24.dp)
            },
            text = news
                .media
        )
        NetworkImage(
            url = news.image.url,
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(source.bottom, 12.dp)
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
            text = news.localedContents.getContents(Locale("ja")).title,
            style = typography.h5,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
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
                    )
                )
            },
            onCheckedChange = {
                onFavoriteChange(news)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsItem() {
    Conferenceapp2021newsTheme {
        val news = News.Other(
            id = "id",
            date = DateTimeTz.nowLocal(),
            collection = "collection",
            image = Image("https://medium.com/droidkaigi/droidkaigi-2020-report-940391367b4e"),
            media = "BLOG",
            localedContents = LocaledContents(
                mapOf(
                    Locale("ja") to LocaledContents.Contents(
                        "very long title very long title very long title",
                        "link")
                    )
                )
            )
                NewsItem (news, false, { }, { })
    }
}
