package io.github.droidkaigi.feeder.contributor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.core.NetworkImage
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.fakeContributors

@Composable
fun ContributorItem(contributor: Contributor, onClickItem: (Contributor) -> Unit) {
    Column(
        modifier = Modifier
            .padding(top = 22.dp)
            .fillMaxHeight()
            .clickable {
                onClickItem(contributor)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            modifier = Modifier
                .padding(top = 10.dp)
                .size(60.dp),
            shape = CircleShape
        ) {
            NetworkImage(
                url = contributor.image,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
                contentDescription = "Contributor Icon"
            )
        }
        Text(
            text = contributor.name,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 12.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContributorItem() {
    ConferenceAppFeederTheme {
        val contributor = fakeContributors().first()
        CompositionLocalProvider(
            provideContributorViewModelFactory {
                fakeContributorViewModel()
            }
        ) {
            ContributorItem(contributor = contributor) {
            }
        }
    }
}
