package io.github.droidkaigi.confnews2021.staff

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StaffList() {
    val (
        state,
        effectFlow,
        dispatch,
    ) = use(staffViewModel())
    Column(Modifier.padding(top = 16.dp)) {
        state.staffContents.forEach {
            Text(it.name)
        }
    }
}
