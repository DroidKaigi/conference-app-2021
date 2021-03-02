package io.github.droidkaigi.feeder.core

import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Contributor
import io.github.droidkaigi.feeder.fakeContributors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun fakeContributorViewModel(errorFetchData: Boolean = false) =
    FakeContributorViewModel(errorFetchData)

class FakeContributorViewModel(errorFetchData: Boolean) : ContributorViewModel {

    private val effectChannel = Channel<ContributorViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<ContributorViewModel.Effect> = effectChannel.receiveAsFlow()

    private val coroutineScope = CoroutineScope(object : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) = block.run()
    })

    private val mutableContributorContents = MutableStateFlow(
        fakeContributors()
    )
    private val errorContributorContents = flow<List<Contributor>> {
        throw AppError.ApiException.ServerException(null)
    }
        .catch { effectChannel.send(ContributorViewModel.Effect.ErrorMessage(it as AppError)) }
        .stateIn(coroutineScope, SharingStarted.Lazily, fakeContributors())

    private val contributorContents =
        if (errorFetchData) errorContributorContents
        else mutableContributorContents

    override val state: StateFlow<ContributorViewModel.State> =
        contributorContents.map { ContributorViewModel.State(contributorContents = it) }
            .stateIn(coroutineScope, SharingStarted.Eagerly, ContributorViewModel.State())

    override fun event(event: ContributorViewModel.Event) {
        coroutineScope.launch {
//            @Exhaustive
//            when (event) {}
        }
    }
}
