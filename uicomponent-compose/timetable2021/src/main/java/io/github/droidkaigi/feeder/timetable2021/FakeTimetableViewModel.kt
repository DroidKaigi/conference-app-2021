package io.github.droidkaigi.feeder.timetable2021

import app.cash.exhaustive.Exhaustive
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Filters
import io.github.droidkaigi.feeder.TimetableContents
import io.github.droidkaigi.feeder.fakeTimetableContents
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

fun fakeTimetableViewModel(errorFetchData: Boolean = false): FakeTimetableViewModel {
    return FakeTimetableViewModel(errorFetchData)
}

class FakeTimetableViewModel(val errorFetchData: Boolean) : TimetableViewModel {

    private val effectChannel = Channel<TimetableViewModel.Effect>(Channel.UNLIMITED)
    override val effect: Flow<TimetableViewModel.Effect> = effectChannel.receiveAsFlow()

    private val coroutineScope = CoroutineScope(
        object : CoroutineDispatcher() {
            // for preview
            override fun dispatch(context: CoroutineContext, block: Runnable) {
                block.run()
            }
        }
    )
    private val mutableSessionContents = MutableStateFlow(
        fakeTimetableContents()
    )
    private val errorSessionContents = flow<TimetableContents> {
        throw AppError.ApiException.ServerException(null)
    }
        .catch { error ->
            effectChannel.send(TimetableViewModel.Effect.ErrorMessage(error as AppError))
        }
        .stateIn(coroutineScope, SharingStarted.Lazily, fakeTimetableContents())

    private val mTimetableContents: StateFlow<TimetableContents> = if (errorFetchData) {
        errorSessionContents
    } else {
        mutableSessionContents
    }

    private val filters: MutableStateFlow<Filters> = MutableStateFlow(Filters())

    override val state: StateFlow<TimetableViewModel.State> =
        combine(mTimetableContents, filters) { feedContents, _ ->
            TimetableViewModel.State(
                timetableContents = feedContents,
            )
        }
            .stateIn(coroutineScope, SharingStarted.Eagerly, TimetableViewModel.State())

    override fun event(event: TimetableViewModel.Event) {
        coroutineScope.launch {
            @Exhaustive
            when (event) {
                is TimetableViewModel.Event.ChangeFavoriteFilter -> {
                    filters.value = event.filters
                }
                is TimetableViewModel.Event.ToggleFavorite -> {
                    val value = mTimetableContents.value
                    val newFavorites = if (!value.favorites.contains(event.session.id)) {
                        value.favorites + event.session.id
                    } else {
                        value.favorites - event.session.id
                    }
                    mutableSessionContents.value = value.copy(
                        favorites = newFavorites
                    )
                }
                is TimetableViewModel.Event.ReloadContent -> {
                    // Sorry, Currently not implemented
                }
            }
        }
    }
}
