package io.github.droidkaigi.feeder.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.droidkaigi.feeder.contributor.provideContributorViewModelFactory
import io.github.droidkaigi.feeder.feed.provideFeedViewModelFactory
import io.github.droidkaigi.feeder.feed.provideFmPlayerViewModelFactory
import io.github.droidkaigi.feeder.provideAppViewModelFactory
import io.github.droidkaigi.feeder.setting.provideSettingViewModelFactory
import io.github.droidkaigi.feeder.staff.provideStaffViewModelFactory
import io.github.droidkaigi.feeder.timetable2021.provideTimetableViewModelFactory

@Composable
fun ProvideViewModels(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        provideAppViewModelFactory { hiltViewModel<RealAppViewModel>() },
        provideFeedViewModelFactory { hiltViewModel<RealFeedViewModel>() },
        provideTimetableViewModelFactory { hiltViewModel<RealTimetableViewModel>() },
        provideSettingViewModelFactory { hiltViewModel<RealSettingViewModel>() },
        provideStaffViewModelFactory { (hiltViewModel<RealStaffViewModel>()) },
        provideContributorViewModelFactory { hiltViewModel<RealContributorViewModel>() },
        provideFmPlayerViewModelFactory { hiltViewModel<RealFmPlayerViewModel>() },
        content = content
    )
}
