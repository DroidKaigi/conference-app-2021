package io.github.droidkaigi.feeder.core.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import io.github.droidkaigi.feeder.LoadState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

@Suppress("ComposableNaming")
@Composable
fun <T> Flow<T>.collectInLaunchedEffect(function: suspend (value: T) -> Unit) {
    val flow = this
    LaunchedEffect(key1 = flow) {
        flow.collect(function)
    }
}

@Composable
fun <T> Flow<T>.collectAsLoadState(): State<LoadState<T>> {
    val flow = this
    return produceState<LoadState<T>>(
        initialValue = LoadState.Loading
    ) {
        flow
            .catch { value = LoadState.Error(it) }
            .collect {
                value = LoadState.Loaded(it)
            }
    }
}
