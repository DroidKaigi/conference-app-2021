package io.github.droidkaigi.feeder.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.droidkaigi.feeder.feed.ProvideFeedViewModel
import io.github.droidkaigi.feeder.staff.ProvideStaffViewModel
import io.github.droidkaigi.feeder.staff.fakeStaffViewModel

@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    ProvideFeedViewModel(viewModel<RealFeedViewModel>()) {
        ProvideStaffViewModel(viewModel = fakeStaffViewModel()) {
            content()
        }
    }
}
