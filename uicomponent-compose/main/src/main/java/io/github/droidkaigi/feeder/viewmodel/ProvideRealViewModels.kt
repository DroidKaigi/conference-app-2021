package io.github.droidkaigi.feeder.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.droidkaigi.feeder.contributor.contributorViewModelProviderValue
import io.github.droidkaigi.feeder.contributor.fakeContributorViewModel
import io.github.droidkaigi.feeder.feed.fmPlayerViewModelProviderValue
import io.github.droidkaigi.feeder.feed.provideFeedViewModelFactory
import io.github.droidkaigi.feeder.provideAppViewModelFactory
import io.github.droidkaigi.feeder.setting.settingViewModelProviderValue
import io.github.droidkaigi.feeder.staff.staffViewModelProviderValue

@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        provideAppViewModelFactory { hiltViewModel<RealAppViewModel>() },
        provideFeedViewModelFactory { hiltViewModel<RealFeedViewModel>() },
        settingViewModelProviderValue(viewModel<RealSettingViewModel>()),
        staffViewModelProviderValue(viewModel<RealStaffViewModel>()),
        contributorViewModelProviderValue(fakeContributorViewModel()),
        fmPlayerViewModelProviderValue(viewModel<RealFmPlayerViewModel>()),
        content = content
    )
}
