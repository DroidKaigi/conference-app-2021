package io.github.droidkaigi.feeder.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import io.github.droidkaigi.feeder.feed.ProvideFeedViewModel
import io.github.droidkaigi.feeder.staff.ProvideStaffViewModel

@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    ProvideFeedViewModel(viewModel<RealFeedViewModel>()) {
        ProvideStaffViewModel(viewModel = fakeStaffViewModel()) {
            content()
        }
    }
}
