package io.github.droidkaigi.feeder.viewmodel

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.droidkaigi.feeder.feed.FmPlayerViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RealFmPlayerViewModel : FmPlayerViewModel, ViewModel() {

    private val effectSharedFlow = Channel<FmPlayerViewModel.Effect>(Channel.UNLIMITED)
    private val mutableState = MutableStateFlow(FmPlayerViewModel.State())

    private val fmPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build()
        )
    }

    private fun play(url: String) {
        fmPlayer.run {
            reset()
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { mp -> mp?.start() }
        }
    }

    private fun restart() {
        fmPlayer.start()
    }

    private fun pause() {
        fmPlayer.run {
            pause()
            setOnPreparedListener(null)
        }
    }

    override val effect: Flow<FmPlayerViewModel.Effect> = effectSharedFlow.receiveAsFlow()
    override val state: StateFlow<FmPlayerViewModel.State> = mutableState

    override fun onCleared() {
        fmPlayer.release()
        super.onCleared()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override fun event(event: FmPlayerViewModel.Event) {
        viewModelScope.launch {
            when (event) {
                is FmPlayerViewModel.Event.ChangePlayerState -> {
                    val state = mutableState.value

                    val isPlay = state.url == event.url &&
                        state.type == FmPlayerViewModel.State.Type.PLAY
                    val isPause = state.url == event.url &&
                        state.type == FmPlayerViewModel.State.Type.PAUSE

                    mutableState.value = state.copy(
                        url = event.url,
                        type = if (isPlay) {
                            FmPlayerViewModel.State.Type.PAUSE
                        } else {
                            FmPlayerViewModel.State.Type.PLAY
                        }
                    )
                    when {
                        isPlay -> pause()
                        isPause -> restart()
                        else -> play(event.url)
                    }
                }
            }
        }
    }
}
