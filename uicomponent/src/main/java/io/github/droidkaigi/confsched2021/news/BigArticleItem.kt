package io.github.droidkaigi.confsched2021.news

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.EmphasisAmbient
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconToggleButton
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.soywiz.klock.DateTimeTz
import io.github.droidkaigi.confsched2021.news.ui.Conferenceapp2021newsTheme
import io.github.droidkaigi.confsched2021.news.ui.typography
import io.github.droidkaigi.confsched2021.news.uicomponent.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BigArticleItem(article: Article) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = ScaffoldStateAmbient.current.snackbarHostState
    ConstraintLayout(
        Modifier
            .clickable {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("TODO: waiting navigation component")
                }
            }
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val (title,
            image,
            section,
            favorite
        ) = createRefs()
        val url = article.image.url
        val modifier = Modifier
            .aspectRatio(16F / 9F)
            .clip(RoundedCornerShape(4.dp))
            .constrainAs(image) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        val contentScale = ContentScale.FillWidth
        UrlImage(url, modifier, contentScale)
        ProvideEmphasis(EmphasisAmbient.current.medium) {
            Text(article.collection, Modifier.constrainAs(section) {
                width = Dimension.fillToConstraints
                linkTo(
                    top = image.bottom,
                    bottom = title.top,
                    bias = 0F,
                    topMargin = 4.dp
                )
                start.linkTo(image.start)
                end.linkTo(favorite.start)
            })
        }
        Text(
            text = article.localedContents.getContents(Locale("ja")).title,
            style = typography.h5,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(title) {
                width = Dimension.fillToConstraints
                linkTo(
                    top = section.bottom,
                    bottom = parent.bottom,
                    bias = 0F,
                    topMargin = 4.dp
                )
                start.linkTo(section.start)
                end.linkTo(section.end)
            }
        )
        IconToggleButton(
            checked = false,
            icon = { Icon(vectorResource(R.drawable.ic_baseline_favorite_24)) },
            onCheckedChange = {
            },
            modifier = Modifier.constrainAs(favorite) {
                top.linkTo(section.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        )
    }
}


@Preview
@Composable
fun BigArticleItemPreview() {
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
        BigArticleItem(article)
    }
}
