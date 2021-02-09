package io.github.droidkaigi.confnews2021.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import io.github.droidkaigi.confnews2021.staff.ProvideStaffViewModel
import io.github.droidkaigi.confnews2021.staff.news.ProvideNewsViewModel

@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    ProvideNewsViewModel(viewModel<RealNewsViewModel>()) {
        ProvideStaffViewModel(viewModel = fakeStaffViewModel()) {
            content()
        }
    }
}
