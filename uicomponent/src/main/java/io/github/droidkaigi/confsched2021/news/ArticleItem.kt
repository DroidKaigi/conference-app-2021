package io.github.droidkaigi.confsched2021.news

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.soywiz.klock.DateTimeTz
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.coil.ErrorResult
import io.github.droidkaigi.confsched2021.news.ui.Conferenceapp2021newsTheme
import io.github.droidkaigi.confsched2021.news.ui.typography

@Composable
fun ArticleItem(article: Article) {
    ConstraintLayout(
        Modifier
            .height(112.dp)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (title, image, section) = createRefs()
        val url = article.image.url
        val modifier = Modifier.constrainAs(image) {
            width = Dimension.value(80.dp)
            height = Dimension.value(80.dp)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
        val contentScale = ContentScale
        Image(url, modifier, contentScale)
        Text(article.collection, Modifier.constrainAs(section) {
            width = Dimension.fillToConstraints
            top.linkTo(parent.top)
            bottom.linkTo(title.top)
            start.linkTo(parent.start)
            end.linkTo(image.start)
        })
        Text(
            text = article.localedContents.getContents(Locale("ja")).title,
            style = typography.h5,
            modifier = Modifier.constrainAs(title) {
                width = Dimension.fillToConstraints
                top.linkTo(parent.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(section.start)
                end.linkTo(section.end)
            }
        )
    }
}

@Composable
fun Image(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale.Companion
) {
    CoilImage(
        data = url,
        modifier = modifier,
        contentScale = contentScale.Crop,
        onRequestCompleted = {
            result->
            when (result) {
                is ErrorResult -> result.throwable.printStackTrace()
            }
        }
    )
}


@Preview
@Composable
fun ArticleItemPreview() {
    Conferenceapp2021newsTheme {
        val article = Article(
            id = "id",
            date = DateTimeTz.nowLocal(),
            collection = "collection",
            image = Image("https://medium.com/droidkaigi/droidkaigi-2020-report-940391367b4e"),
            media = "BLOG",
            LocaledContents(
                mapOf(
                    Locale("ja") to LocaledContents.Contents("title", "link")
                )
            )
        )
        ArticleItem(article)
    }
}
