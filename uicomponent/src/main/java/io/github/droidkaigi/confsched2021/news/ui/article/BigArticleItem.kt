package io.github.droidkaigi.confsched2021.news.ui.article

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.Dimension
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AmbientEmphasisLevels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconToggleButton
import androidx.compose.material.ProvideEmphasis
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.soywiz.klock.DateTimeTz
import io.github.droidkaigi.confsched2021.news.Article
import io.github.droidkaigi.confsched2021.news.Image
import io.github.droidkaigi.confsched2021.news.Locale
import io.github.droidkaigi.confsched2021.news.LocaledContents
import io.github.droidkaigi.confsched2021.news.ui.theme.Conferenceapp2021newsTheme
import io.github.droidkaigi.confsched2021.news.ui.theme.typography
import io.github.droidkaigi.confsched2021.news.uicomponent.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BigArticleItem(
    article: Article,
    onClick: (Article) -> Unit,
    onFavoriteChange: (Article) -> Unit
) {
    ConstraintLayout(
        Modifier
            .clickable(onClick = { onClick(article) })
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
        NetworkImage(url, modifier, contentScale)
        Text(
            text = article.localedContents.getContents(Locale("ja")).title,
            style = typography.h5,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.constrainAs(title) {
                width = Dimension.fillToConstraints
                linkTo(
                    top = image.bottom,
                    bottom = section.bottom,
                    bias = 0F,
                    topMargin = 4.dp
                )
                start.linkTo(section.start)
                end.linkTo(section.end)
            }
        )
        ProvideEmphasis(AmbientEmphasisLevels.current.medium) {
            Text(article.collection, Modifier.constrainAs(section) {
                width = Dimension.fillToConstraints
                linkTo(
                    top = title.bottom,
                    bottom = parent.bottom,
                    bias = 0F,
                    topMargin = 4.dp
                )
                start.linkTo(image.start)
                end.linkTo(favorite.start)
            })
        }
        IconToggleButton(
            checked = false,
            icon = {
                Icon(
                    vectorResource(
                        if (article.isFavorited) {
                            R.drawable
                                .ic_baseline_favorite_24
                        } else {
                            R.drawable
                                .ic_baseline_favorite_border_24
                        }
                    )
                )
            },
            onCheckedChange = { onFavoriteChange(article) },
            modifier = Modifier.constrainAs(favorite) {
                top.linkTo(title.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun BigArticleItemPreview() {
    Conferenceapp2021newsTheme {
        val article = Article(
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
        BigArticleItem(article, { }, {})
    }
}
