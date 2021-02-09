package io.github.droidkaigi.confnews2021.staff

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.ambientOf
import io.github.droidkaigi.confnews2021.AppError
import io.github.droidkaigi.confnews2021.Staff
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface StaffViewModel :
    UnidirectionalViewModel<StaffViewModel.Event, StaffViewModel.Effect, StaffViewModel.State> {
    data class State(
        val showProgress: Boolean = false,
        val staffContents: List<Staff> = listOf(),
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
fun staffViewModel() = AmbientStaffViewModel.current

