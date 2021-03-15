package io.github.droidkaigi.feeder.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.droidkaigi.feeder.ProvideAppViewModel
import io.github.droidkaigi.feeder.contributor.ProvideContributorViewModel
import io.github.droidkaigi.feeder.contributor.fakeContributorViewModel
import io.github.droidkaigi.feeder.feed.ProvideFeedViewModel
import io.github.droidkaigi.feeder.feed.ProvideFmPlayerViewModel
import io.github.droidkaigi.feeder.setting.ProvideSettingViewModel
import io.github.droidkaigi.feeder.staff.ProvideStaffViewModel

@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    ProvideAppViewModel(viewModel<RealAppViewModel>()) {
        ProvideFeedViewModel(viewModel<RealFeedViewModel>()) {
            ProvideSettingViewModel(viewModel<RealSettingViewModel>()) {
                ProvideStaffViewModel(viewModel<RealStaffViewModel>()) {
                    ProvideContributorViewModel(viewModel = fakeContributorViewModel()) {
                        ProvideFmPlayerViewModel(viewModel<RealFmPlayerViewModel>()) {
                            content()
                        }
                    }
                }
            }
        }
    }
}
