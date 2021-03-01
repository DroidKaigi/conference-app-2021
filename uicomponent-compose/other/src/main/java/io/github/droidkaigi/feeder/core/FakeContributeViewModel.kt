package io.github.droidkaigi.feeder.core

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeContributeViewModel: ContributorViewModel {

    private val effectChannel = Channel<ContributorViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<ContributorViewModel.Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<ContributorViewModel.State>
        get() = TODO("Not yet implemented")

    override fun event(event: ContributorViewModel.Event) {
        TODO("Not yet implemented")
    }
}
