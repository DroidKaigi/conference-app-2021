package io.github.droidkaigi.feeder

import android.app.Application
import dagger.hilt.android.testing.HiltTestApplication
import io.github.droidkaigi.feeder.core.util.SessionAlarm
import io.github.droidkaigi.feeder.data.TimetableRepositoryImpl
import io.github.droidkaigi.feeder.data.fakeDroidKaigi2021Api
import io.github.droidkaigi.feeder.data.fakeTimetableItemDao
import io.github.droidkaigi.feeder.data.fakeUserDataStore
import io.github.droidkaigi.feeder.timetable2021.TimetableDetailViewModel
import io.github.droidkaigi.feeder.timetable2021.fakeTimetableDetailViewModel
import io.github.droidkaigi.feeder.viewmodel.RealTimetableDetailViewModel
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
class TimetableDetailViewModelTest(
    @Suppress("unused")
    val name: String,
    private val timetableDetailViewModelFactory: TimetableDetailViewModelFactory,
) {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun contents() = coroutineTestRule.testDispatcher.runBlockingTest {
        // Replace when it fixed https://github.com/cashapp/turbine/issues/10
        val timetableDetailViewModel = timetableDetailViewModelFactory.create()

        val firstContent = timetableDetailViewModel.state.value.timetableContents.timetableItems

        firstContent.timetableItems.size shouldBeGreaterThan 1
    }

    @Test
    fun errorWhenFetch() = coroutineTestRule.testDispatcher.runBlockingTest {
        val timetableDetailViewModel = timetableDetailViewModelFactory.create(errorFetchData = true)
        val state = timetableDetailViewModel.state.value

        val firstEffect = timetableDetailViewModel.effect.first()

        firstEffect.shouldBeInstanceOf<TimetableDetailViewModel.Effect.ErrorMessage>()
    }

    class TimetableDetailViewModelFactory(
        private val viewModelFactory: (errorFetchData: Boolean) ->
        TimetableDetailViewModel,
    ) {
        fun create(
            errorFetchData: Boolean = false,
        ): TimetableDetailViewModel {
            return viewModelFactory(errorFetchData)
        }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data() = listOf(
            arrayOf(
                "Real ViewModel and Repository",
                TimetableDetailViewModelFactory { errorFetchData: Boolean ->
                    RealTimetableDetailViewModel(
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
                        sessionAlarm = SessionAlarm(
                            Application()
                        )
                    )
                }
            ),
            arrayOf(
                "FakeViewModel",
                TimetableDetailViewModelFactory { errorFetchData: Boolean ->
                    fakeTimetableDetailViewModel(errorFetchData = errorFetchData)
                }
            )
        )
    }
}
