package io.github.droidkaigi.feeder.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class FakeContributeViewModel: ContributorViewModel {

    override val effect: Flow<ContributorViewModel.Effect>
        get() = TODO("Not yet implemented")

    override val state: StateFlow<ContributorViewModel.State>
        get() = TODO("Not yet implemented")

    override fun event(event: ContributorViewModel.Event) {
        TODO("Not yet implemented")
    }
}
