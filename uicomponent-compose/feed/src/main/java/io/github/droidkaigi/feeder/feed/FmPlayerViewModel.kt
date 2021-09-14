package io.github.droidkaigi.feeder.feed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.core.UnidirectionalViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FmPlayerViewModel : UnidirectionalViewModel<FmPlayerViewModel.Event,
        FmPlayerViewModel.Effect,
        FmPlayerViewModel.State> {

    data class State(
        val url: String? = null,
        val type: Type = Type.STOP,
    ) {
        enum class Type { PLAY, PAUSE, STOP }

        fun isPlaying() = type == Type.PLAY
    }

    sealed class Effect

    sealed class Event {
        data class ChangePlayerState(val url: String) : Event()
    }

    override val effect: Flow<Effect>
    override val state: StateFlow<State>
    override fun event(event: Event)
}

private val LocalFmPlayerViewModelFactory =
    compositionLocalOf<@Composable () -> FmPlayerViewModel> {
        {
            error("not LocalFmPlayerViewModel provided")
        }
}

fun provideFmPlayerViewModelFactory(viewModelFactory: @Composable () -> FmPlayerViewModel) =
    LocalFmPlayerViewModelFactory provides viewModelFactory

@Composable
fun fmPlayerViewModel() = LocalFmPlayerViewModelFactory.current()
