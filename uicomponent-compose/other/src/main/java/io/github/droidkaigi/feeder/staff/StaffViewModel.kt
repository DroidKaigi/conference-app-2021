package io.github.droidkaigi.feeder.staff

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Staff
import io.github.droidkaigi.feeder.core.UnidirectionalViewModel
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

    sealed class Event

    override val state: StateFlow<State>
    override val effect: Flow<Effect>
    override fun event(event: Event)
}

private val LocalStaffViewModelFactory = compositionLocalOf<@Composable () -> StaffViewModel> {
    {
        error("Not view model provided")
    }
}

fun provideStaffViewModelFactory(viewModelFactory: @Composable () -> StaffViewModel) =
    LocalStaffViewModelFactory provides viewModelFactory

@Composable
fun staffViewModel() = LocalStaffViewModelFactory.current()
