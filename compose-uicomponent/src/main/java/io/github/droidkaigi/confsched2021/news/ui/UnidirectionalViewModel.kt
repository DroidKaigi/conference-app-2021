package io.github.droidkaigi.confsched2021.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Composable
inline fun <reified EVENT, EFFECT, STATE> use(viewModel: UnidirectionalViewModel<EVENT, EFFECT, STATE>):
    Triple<STATE, Flow<EFFECT>, (EVENT) -> Unit> {
    val state by viewModel.state.collectAsState()

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.event(event)
    }
    return Triple(state, viewModel.effect, dispatch)
}

interface UnidirectionalViewModel<EVENT, EFFECT, STATE> {
    val state: StateFlow<STATE>
    val effect: Flow<EFFECT>
    fun event(event: EVENT)
}
