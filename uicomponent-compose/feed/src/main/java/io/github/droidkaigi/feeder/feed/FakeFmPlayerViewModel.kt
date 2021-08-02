package io.github.droidkaigi.feeder.feed

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

fun fakeFmPlayerViewModel(): FakeFmPlayerViewModel {
    return FakeFmPlayerViewModel()
}

class FakeFmPlayerViewModel : FmPlayerViewModel {
    private val effectSharedFlow = Channel<FmPlayerViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<FmPlayerViewModel.Effect>
        get() =  effectSharedFlow.receiveAsFlow()
    override val state: StateFlow<FmPlayerViewModel.State>
        get() = MutableStateFlow(FmPlayerViewModel.State())

    override fun event(event: FmPlayerViewModel.Event) {
    }
}
