package io.github.droidkaigi.feeder

import android.app.Application
import io.github.droidkaigi.feeder.core.util.TimetableItemAlarm
import io.github.droidkaigi.feeder.data.TimetableRepositoryImpl
import io.github.droidkaigi.feeder.data.fakeDroidKaigi2021Api
import io.github.droidkaigi.feeder.data.fakeTimetableItemDao
import io.github.droidkaigi.feeder.data.fakeUserDataStore
import io.github.droidkaigi.feeder.timetable2021.TimetableViewModel
import io.github.droidkaigi.feeder.timetable2021.fakeTimetableViewModel
import io.github.droidkaigi.feeder.viewmodel.RealTimetableViewModel
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@InternalCoroutinesApi
@RunWith(Parameterized::class)
class TimetableViewModelTest(
    @Suppress("unused")
    val name: String,
    private val timetableViewModelFactory: TimetableViewModelFactory,
) {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun contents() = coroutineTestRule.testDispatcher.runBlockingTest {
        // Replace when it fixed https://github.com/cashapp/turbine/issues/10
        val timetableViewModel = timetableViewModelFactory.create()

        val firstContent = timetableViewModel.state.value.timetableContents.timetableItems

        firstContent.timetableItems.size shouldBeGreaterThan 1
    }

    @Test
    fun errorWhenFetch() = coroutineTestRule.testDispatcher.runBlockingTest {
        val timetableViewModel = timetableViewModelFactory.create(errorFetchData = true)
        val state = timetableViewModel.state.value

        val firstEffect = timetableViewModel.effect.first()

        firstEffect.shouldBeInstanceOf<TimetableViewModel.Effect.ErrorMessage>()
    }

    class TimetableViewModelFactory(
        private val viewModelFactory: (errorFetchData: Boolean) ->
        TimetableViewModel,
    ) {
        fun create(
            errorFetchData: Boolean = false,
        ): TimetableViewModel {
            return viewModelFactory(errorFetchData)
        }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data() = listOf(
            arrayOf(
                "Real ViewModel and Repository",
                TimetableViewModelFactory { errorFetchData: Boolean ->
                    RealTimetableViewModel(
                        repository = TimetableRepositoryImpl(
                            droidKaigi2021Api = fakeDroidKaigi2021Api(
                                if (errorFetchData) {
                                    AppError.ApiException.ServerException(null)
                                } else {
                                    null
                                }
                            ),
                            timetableItemDao = fakeTimetableItemDao(
                                if (errorFetchData) {
                                    AppError.UnknownException(Throwable("Database Exception"))
                                } else {
                                    null
                                }
                            ),
                            dataStore = fakeUserDataStore()
                        ),
                        timetableItemAlarm = TimetableItemAlarm(
                            Application()
                        )
                    )
                }
            ),
            arrayOf(
                "FakeViewModel",
                TimetableViewModelFactory { errorFetchData: Boolean ->
                    fakeTimetableViewModel(errorFetchData = errorFetchData)
                }
            )
        )
    }
}
