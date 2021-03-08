package io.github.droidkaigi.feeder.contributor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.feeder.core.use

@Composable
fun ContributorList() {
    val (
        state,
        effectFlow,
        dispatch,
    ) = use(contributeViewModel())
    Column(Modifier.padding(top = 32.dp)) {
        Text("Not implemented yet. Please create this screen!")
        state.contributorContents.forEach {
            Text(it.name)
        }
    }
}
