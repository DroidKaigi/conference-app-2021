package io.github.droidkaigi.feeder.viewmodel

import io.github.droidkaigi.feeder.fakeStaffs
import io.github.droidkaigi.feeder.staff.StaffViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

fun fakeStaffViewModel() = FakeStaffViewModel()
class FakeStaffViewModel : StaffViewModel {
    override val state: StateFlow<StaffViewModel.State> = MutableStateFlow(
        StaffViewModel.State(
            showProgress = false,
            staffContents = fakeStaffs()
        )
    )

    private val effectChannel = Channel<StaffViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<StaffViewModel.Effect> = effectChannel.receiveAsFlow()
    override fun event(event: StaffViewModel.Event) {
    }
}
