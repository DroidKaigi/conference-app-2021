package io.github.droidkaigi.feeder.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.droidkaigi.feeder.contributor.fakeContributorViewModel
import io.github.droidkaigi.feeder.contributor.provideContributorViewModelFactory
import io.github.droidkaigi.feeder.feed.provideFeedViewModelFactory
import io.github.droidkaigi.feeder.feed.provideFmPlayerViewModelFactory
import io.github.droidkaigi.feeder.provideAppViewModelFactory
import io.github.droidkaigi.feeder.setting.provideSettingViewModelFactory
import io.github.droidkaigi.feeder.staff.provideStaffViewModelFactory

@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        provideAppViewModelFactory { hiltViewModel<RealAppViewModel>() },
        provideFeedViewModelFactory { hiltViewModel<RealFeedViewModel>() },
        provideSettingViewModelFactory { hiltViewModel<RealSettingViewModel>() },
        provideStaffViewModelFactory { (hiltViewModel<RealStaffViewModel>()) },
        provideContributorViewModelFactory { fakeContributorViewModel() },
        provideFmPlayerViewModelFactory { hiltViewModel<RealFmPlayerViewModel>() },
        content = content
    )
}
