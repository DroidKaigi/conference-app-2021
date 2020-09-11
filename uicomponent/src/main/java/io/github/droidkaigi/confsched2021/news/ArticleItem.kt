package io.github.droidkaigi.confsched2021.news

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconToggleButton
import androidx.compose.material.ListItem
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.soywiz.klock.DateTimeTz
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.coil.ErrorResult
import io.github.droidkaigi.confsched2021.news.ui.Conferenceapp2021newsTheme
import io.github.droidkaigi.confsched2021.news.ui.typography
import io.github.droidkaigi.confsched2021.news.uicomponent.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleItem(article: Article) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = ScaffoldStateAmbient.current.snackbarHostState
    ListItem(
        modifier = Modifier
            .clickable {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("TODO: waiting navigation component")
                }
            },
        icon = {
            val url = article.image.url
            val modifier = Modifier
                .width(64.dp)
                .clip(RoundedCornerShape(4.dp))
                .aspectRatio(16F / 9F)
            NetworkImage(url, modifier, ContentScale.Inside)
        },
        secondaryText = {
            Text(
                article.collection,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        trailing = {
            IconToggleButton(
                checked = false,
                icon = { Icon(vectorResource(R.drawable.ic_baseline_favorite_24)) },
                onCheckedChange = {

                }
            )
        }
    ) {
        Text(
            text = article.localedContents.getContents(Locale("ja")).title,
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
                is ErrorResult -> result.throwable.printStackTrace()
            }
        }
    )
}

@Preview(showBackground = true)
@Preview
@Composable
fun ArticleItemPreview() {
    Conferenceapp2021newsTheme {
        Providers(ScaffoldStateAmbient provides rememberScaffoldState()) {
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
}
