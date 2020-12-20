package io.github.droidkaigi.confsched2021.news.ui.news

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soywiz.klock.DateTimeTz
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.imageloading.ImageLoadState
import io.github.droidkaigi.confsched2021.news.Image
import io.github.droidkaigi.confsched2021.news.Locale
import io.github.droidkaigi.confsched2021.news.LocaledContents
import io.github.droidkaigi.confsched2021.news.News
import io.github.droidkaigi.confsched2021.news.ui.theme.Conferenceapp2021newsTheme
import io.github.droidkaigi.confsched2021.news.ui.theme.typography
import io.github.droidkaigi.confsched2021.news.uicomponent.R

@Composable
fun NewsItem(
    news: News,
    favorited: Boolean,
    onClick: (News) -> Unit,
    onFavoriteChange: (News) -> Unit
) {
    ListItem(
        modifier = Modifier
            .clickable(onClick = { onClick(news) }),
        icon = {
            val url = news.image.url
            val modifier = Modifier
                .width(64.dp)
                .clip(RoundedCornerShape(4.dp))
                .aspectRatio(16F / 9F)
            NetworkImage(url, modifier, ContentScale.Inside)
        },
        secondaryText = {
            Text(
                text = news.collection,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        trailing = {
            IconToggleButton(
                checked = false,
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
    ) {
        Text(
            text = news.localedContents.getContents(Locale("ja")).title,
            style = typography.h5,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun NetworkImage(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale
) {
//    Text("(image waiting for update)", modifier = modifier)
    CoilImage(
        data = url,
        modifier = modifier,
        contentScale = contentScale,
        onRequestCompleted = { result ->
            when (result) {
                is ImageLoadState.Error -> result.throwable.printStackTrace()
            }
        }
    )
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
                    Locale("ja") to LocaledContents.Contents("title", "link")
                )
            )
        )
        NewsItem(news, false, { }, { })
    }
}
