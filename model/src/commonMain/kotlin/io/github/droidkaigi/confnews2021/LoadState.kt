package io.github.droidkaigi.confnews2021

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class LoadState<out T> {

    object Loading : LoadState<Nothing>()
    data class Loaded<T>(val value: T) : LoadState<T>()
    data class Error(val e: AppError) : LoadState<Nothing>() {
        constructor(e: Throwable) : this(
            if (e is AppError) {
                e
            } else {
                AppError.UnknownException(e)
            }
        )
    }

    @OptIn(ExperimentalContracts::class)
    fun isLoading(): Boolean {
        contract {
            returns(true) implies (this@LoadState is Loading)
        }
        return this is Loading
    }

    @OptIn(ExperimentalContracts::class)
    fun isError(): Boolean {
        contract {
            returns(true) implies (this@LoadState is Error)
        }
        return this is Error
    }

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

fun <T> Flow<T>.toLoadState(): Flow<LoadState<T>> =
    map { LoadState.Loaded(it) as LoadState<T> }
        .onStart { emit(LoadState.Loading) }
        .catch { cause -> emit(LoadState.Error(cause)) }
