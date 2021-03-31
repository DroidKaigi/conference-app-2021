package io.github.droidkaigi.feeder.staff

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.toPaddingValues
import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.core.theme.ConferenceAppFeederTheme
import io.github.droidkaigi.feeder.core.use

@Composable
fun StaffList(onStaffClick: (Staff) -> Unit) {
    val (
        state,
        effectFlow,
        dispatch,
    ) = use(staffViewModel())

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxHeight()
            .padding(vertical = 48.dp)
    ) {
        LazyColumn(
            contentPadding = LocalWindowInsets.current.systemBars
                .toPaddingValues(top = false, start = false, end = false),
            content = {
                items(state.staffContents) { staff ->
                    StaffItem(staff = staff, onStaffClick)
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStaffScreen() {
    ConferenceAppFeederTheme {
        CompositionLocalProvider(staffViewModelProviderValue(fakeStaffViewModel())) {
            StaffList {}
        }
    }
}
