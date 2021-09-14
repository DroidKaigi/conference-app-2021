package io.github.droidkaigi.feeder.contributor

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.toPaddingValues
import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.core.use

@Composable
fun ContributorList(onContributorClick: (Contributor) -> Unit) {

    val (
        state,
        effectFlow,
        dispatch,
    ) = use(contributeViewModel())

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxHeight()
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(count = 3),
            contentPadding = LocalWindowInsets.current.systemBars
                .toPaddingValues(top = false, start = false, end = false),
            content = {
                items(state.contributorContents) { contributor ->
                    ContributorItem(contributor = contributor, onContributorClick)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewContributorScreen() {
    ConferenceAppFeederTheme {
        CompositionLocalProvider(provideContributorViewModelFactory { fakeContributorViewModel() }) {
            ContributorList() {
            }
        }
    }
}
