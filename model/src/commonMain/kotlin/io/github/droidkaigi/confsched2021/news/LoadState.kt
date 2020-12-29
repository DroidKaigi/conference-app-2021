package io.github.droidkaigi.confsched2021.news

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

sealed class LoadState<out T> {

    object Loading : LoadState<Nothing>()
    data class Loaded<T>(val value: T) : LoadState<T>()
    data class Error(val e: Throwable) : LoadState<Nothing>()

    val isLoading: Boolean get() = this is Loading

    fun getValueOrNull(): T? {
        if (this is Loaded<T>) {
            return value
        }
        return null
    }

    fun getThrowableOrNull(): Throwable? {
        if (this is Error) {
            return e
        }
        return null
    }
}

fun <T> Flow<T>.toLoadStateIn(scope: CoroutineScope) =
    map { LoadState.Loaded(it) as LoadState<T> }
        .onStart { emit(LoadState.Loading) }
        .onCompletion { cause -> if (cause != null) emit(LoadState.Error(cause)) }
        .stateIn(scope, SharingStarted.Eagerly, LoadState.Loading)