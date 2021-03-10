package io.github.droidkaigi.feeder.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.droidkaigi.feeder.ProvideDroidKaigiAppViewModel
import io.github.droidkaigi.feeder.contributor.ProvideContributorViewModel
import io.github.droidkaigi.feeder.contributor.fakeContributorViewModel
import io.github.droidkaigi.feeder.feed.ProvideFeedViewModel
import io.github.droidkaigi.feeder.setting.ProvideSettingViewModel
import io.github.droidkaigi.feeder.staff.ProvideStaffViewModel
import io.github.droidkaigi.feeder.staff.fakeStaffViewModel

@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    ProvideDroidKaigiAppViewModel(viewModel<RealDroidKaigiAppViewModel>()) {
        ProvideFeedViewModel(viewModel<RealFeedViewModel>()) {
            ProvideSettingViewModel(viewModel<RealSettingViewModel>()) {
                ProvideStaffViewModel(viewModel = fakeStaffViewModel()) {
                    ProvideContributorViewModel(viewModel = fakeContributorViewModel()) {
                        content()
                    }
                }
            }
        }
    }
}
