package io.github.droidkaigi.confnews2021.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.droidkaigi.confnews2021.staff.StaffViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class RealStaffViewModel @Inject constructor(
//    private val repository: StaffRepository,
) : ViewModel(), StaffViewModel {
    override val state: StateFlow<StaffViewModel.State> = TODO()
    private val effectChannel = Channel<StaffViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<StaffViewModel.Effect> = effectChannel.receiveAsFlow()
    override fun event(event: StaffViewModel.Event) {
        TODO("Not yet implemented")
    }
}
