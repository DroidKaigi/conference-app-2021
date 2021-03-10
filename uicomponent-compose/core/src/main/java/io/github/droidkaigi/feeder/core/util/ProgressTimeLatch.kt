package io.github.droidkaigi.feeder.core.util

import androidx.annotation.MainThread
import androidx.annotation.VisibleForTesting
import kotlin.properties.Delegates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProgressTimeLatch(
    private val viewModelScope: CoroutineScope,
    private val delayMs: Long = 750,
    private val minShowTime: Long = 500,
    private val currentTimeProvider: (() -> Long) = { System.currentTimeMillis() }
) {
    private sealed class State(val showProgress: Boolean) {
        object NotRefreshNoProgress : State(false)
        object RefreshNoProgress : State(false)
        class RefreshProgress(val startTime: Long) : State(true)
        object NotRefreshProgress : State(true)
    }

    private val _toggleState = MutableStateFlow(false)
    val toggleState: StateFlow<Boolean> = _toggleState

    private var visibility: Boolean? by Delegates.observable(null) { property, oldValue, newValue ->
        if (oldValue != newValue && newValue != null) {
            _toggleState.value = newValue
        }
    }

    private var state: State = State.NotRefreshNoProgress
        set(value) {
            visibility = value.showProgress
            field = value
        }

    fun refresh(newRefreshing: Boolean) {
        viewModelScope.launch {
            refreshLogic(newRefreshing)
        }
    }

    @MainThread
    @VisibleForTesting
    internal suspend fun refreshLogic(newRefreshing: Boolean) {
        when (val localState = state) {
            State.NotRefreshNoProgress -> {
                if (newRefreshing) {
                    state = State.RefreshNoProgress
                    delay(delayMs)
                    if (state == State.RefreshNoProgress) {
                        state = State.RefreshProgress(currentTimeProvider())
                    }
                }
            }
            State.RefreshNoProgress -> {
                if (newRefreshing) {
                    // do nothing
                } else {
                    state = State.NotRefreshNoProgress
                }
            }
            is State.RefreshProgress -> {
                if (newRefreshing) {
                    // do nothing
                } else {
                    val shouldWaitTime =
                        minShowTime - (currentTimeProvider() - localState.startTime)
                    val shouldWait = shouldWaitTime > 0
                    state = State.NotRefreshProgress
                    if (shouldWait) {
                        delay(shouldWaitTime)
                    }
                    if (state == State.NotRefreshProgress) {
                        state = State.NotRefreshNoProgress
                    }
                }
            }
            State.NotRefreshProgress -> {
                if (newRefreshing) {
                    state = State.RefreshProgress(currentTimeProvider())
                }
            }
        }
    }
}
