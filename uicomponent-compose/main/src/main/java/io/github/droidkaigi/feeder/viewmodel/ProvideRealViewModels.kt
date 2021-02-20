package io.github.droidkaigi.feeder.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import io.github.droidkaigi.feeder.staff.ProvideStaffViewModel
import io.github.droidkaigi.feeder.staff.news.ProvideNewsViewModel

@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    ProvideNewsViewModel(viewModel<RealNewsViewModel>()) {
        ProvideStaffViewModel(viewModel = fakeStaffViewModel()) {
            content()
        }
    }
}
