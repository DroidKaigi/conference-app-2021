package io.github.droidkaigi.feeder.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import io.github.droidkaigi.feeder.core.ProvideStaffViewModel
import io.github.droidkaigi.feeder.feed.ProvideFeedViewModel

@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    ProvideFeedViewModel(viewModel<RealFeedViewModel>()) {
        ProvideStaffViewModel(viewModel = fakeStaffViewModel()) {
            content()
        }
    }
}
