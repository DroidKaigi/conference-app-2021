package io.github.droidkaigi.feeder.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.coroutines.CoroutineContext

class FakeContributeViewModel: ContributorViewModel {

    private val effectChannel = Channel<ContributorViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<ContributorViewModel.Effect> = effectChannel.receiveAsFlow()

    private val coroutineScope = CoroutineScope(object: CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) = block.run()
    })

    override val state: StateFlow<ContributorViewModel.State>
        get() = TODO("Not yet implemented")

    override fun event(event: ContributorViewModel.Event) {
        TODO("Not yet implemented")
    }
}
