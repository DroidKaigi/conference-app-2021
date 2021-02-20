package io.github.droidkaigi.feeder.core

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
    Column(Modifier.padding(top = 32.dp)) {
        Text("Not implemented yet. Please create this screen!")
        state.staffContents.forEach {
            Text(it.name)
        }
    }
}
