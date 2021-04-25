package io.github.droidkaigi.feeder

import kotlin.native.concurrent.freeze
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NonNullSuspendWrapper<T : Any>(private val suspender: suspend () -> T) {
    init {
        freeze()
    }

    fun subscribe(
        scope: CoroutineScope,
        onSuccess: (result: T) -> Unit,
        onFailure: (error: Throwable) -> Unit
    ) = scope.launch {
        runCatching {
            suspender()
        }.onSuccess {
            onSuccess(it.freeze())
        }.onFailure {
            onFailure(it.freeze())
        }
    }
}

class NonNullFlowWrapper<T : Any>(private val flow: Flow<T>) {
    init {
        freeze()
    }

    fun subscribe(
        scope: CoroutineScope,
        onEach: (item: T) -> Unit,
        onComplete: () -> Unit = {},
        onFailure: (error: Throwable) -> Unit = {}
    ) = flow
        .onEach { onEach(it.freeze()) }
        .catch { onFailure(it.freeze()) }
        .onCompletion { onComplete() }
        .launchIn(scope)
        .freeze()
}

class NullableFlowWrapper<T>(private val flow: Flow<T>) {
    init {
        freeze()
    }

    fun subscribe(
        scope: CoroutineScope,
        onEach: (item: T) -> Unit,
        onComplete: () -> Unit = {},
        onFailure: (error: Throwable) -> Unit = {}
    ) = flow
        .onEach { onEach(it.freeze()) }
        .catch { onFailure(it.freeze()) }
        .onCompletion { onComplete() }
        .launchIn(scope)
        .freeze()
}
