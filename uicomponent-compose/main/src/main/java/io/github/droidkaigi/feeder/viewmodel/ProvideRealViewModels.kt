package io.github.droidkaigi.feeder.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
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
