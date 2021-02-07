package io.github.droidkaigi.confsched2021.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import io.github.droidkaigi.confsched2021.news.AppError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface StaffViewModel :
    UnidirectionalViewModel<StaffViewModel.Event, StaffViewModel.Effect, StaffViewModel.State> {
    data class State(
        val showProgress: Boolean = false,
//        val staffContents: StaffContents = StaffContents(),
    )

    sealed class Effect {
        data class ErrorMessage(val appError: AppError) : Effect()
    }

    sealed class Event {
    }

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val AmbientStaffViewModel = ambientOf<StaffViewModel>()

@Composable
fun ProvideStaffViewModel(viewModel: StaffViewModel, block: @Composable () -> Unit) {
    Providers(AmbientStaffViewModel provides viewModel, content = block)
}

@Composable
fun newsViewModel() = AmbientStaffViewModel.current

