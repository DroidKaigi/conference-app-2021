package io.github.droidkaigi.feeder.viewmodel

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.droidkaigi.feeder.feed.FmPlayerViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RealFmPlayerViewModel : FmPlayerViewModel, ViewModel() {

    private val effectSharedFlow = MutableSharedFlow<FmPlayerViewModel.Effect>()
    private val mutableState = MutableStateFlow(FmPlayerViewModel.State())

    private val fmPlayer = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build()
        )
    }

    override val effect: Flow<FmPlayerViewModel.Effect> = effectSharedFlow
    override val state: StateFlow<FmPlayerViewModel.State> = mutableState

    override fun onCleared() {
        fmPlayer.release()
        super.onCleared()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    override fun event(event: FmPlayerViewModel.Event) {
        viewModelScope.launch {
            when (event) {
                is FmPlayerViewModel.Event.PlayFmPlayer -> with(fmPlayer) {
                    val state = mutableState.value
                    val newState =
                        FmPlayerViewModel.State(event.url, FmPlayerViewModel.State.Type.PLAY)

                    if (state.url == newState.url) {
                        start()
                    } else {
                        reset()
                        setDataSource(newState.url)
                        prepare()
                        start()
                    }
                    mutableState.emit(newState)
                }
                is FmPlayerViewModel.Event.PauseFmPlayer -> with(fmPlayer) {
                    pause()
                    val newValue = mutableState.value.copy(
                        type = FmPlayerViewModel.State.Type.PAUSE
                    )
                    mutableState.emit(newValue)
                }
            }
        }
    }

}
