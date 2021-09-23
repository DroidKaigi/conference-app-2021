package io.github.droidkaigi.feeder

import io.github.droidkaigi.feeder.data.TimetableRepositoryImpl
import io.github.droidkaigi.feeder.data.fakeDroidKaigi2021Api
import io.github.droidkaigi.feeder.data.fakeTimetableItemDao
import io.github.droidkaigi.feeder.data.fakeUserDataStore
import io.github.droidkaigi.feeder.timetable2021.TimetableViewModel
import io.github.droidkaigi.feeder.timetable2021.TimetableViewModel.Event.ChangeFavoriteFilter
import io.github.droidkaigi.feeder.timetable2021.TimetableViewModel.Event.ToggleFavorite
import io.github.droidkaigi.feeder.timetable2021.fakeTimetableViewModel
import io.github.droidkaigi.feeder.viewmodel.RealTimetableViewModel
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
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

        val firstContent = timetableViewModel.state.value.filteredTimetableContents.timetableItems

        firstContent.timetableItems.size shouldBeGreaterThan 1
    }

    @Test
    fun errorWhenFetch() = coroutineTestRule.testDispatcher.runBlockingTest {
        val timetableViewModel = timetableViewModelFactory.create(errorFetchData = true)
        val state = timetableViewModel.state.value

        val firstEffect = timetableViewModel.effect.first()

        firstEffect.shouldBeInstanceOf<TimetableViewModel.Effect.ErrorMessage>()
    }

    @Test
    fun favorite_Add() = coroutineTestRule.testDispatcher.runBlockingTest {
        val timetableViewModel = timetableViewModelFactory.create()
        val firstContent = timetableViewModel.state.value.filteredTimetableContents
        firstContent.favorites shouldBe setOf()

        timetableViewModel.event(ToggleFavorite(firstContent.timetableItems[0]))

        val secondContent = timetableViewModel.state.value.filteredTimetableContents
        secondContent.favorites shouldBe setOf(firstContent.timetableItems[0].id)
    }

    @Test
    fun favorite_Remove() = coroutineTestRule.testDispatcher.runBlockingTest {
        val timetableViewModel = timetableViewModelFactory.create()
        val firstContent = timetableViewModel.state.value.filteredTimetableContents
        firstContent.favorites shouldBe setOf()

        timetableViewModel.event(ToggleFavorite(firstContent.timetableItems[0]))
        timetableViewModel.event(ToggleFavorite(firstContent.timetableItems[0]))

        val secondContent = timetableViewModel.state.value.filteredTimetableContents
        secondContent.favorites shouldBe setOf()
    }

    @Test
    fun favorite_Filter() = coroutineTestRule.testDispatcher.runBlockingTest {
        val timetableViewModel = timetableViewModelFactory.create()
        val firstContent = timetableViewModel.state.value.filteredTimetableContents
        firstContent.favorites shouldBe setOf()
        val favoriteContent = firstContent.timetableItems[1]

        timetableViewModel.event(ToggleFavorite(favoriteContent))
        timetableViewModel.event(ChangeFavoriteFilter(Filters(filterFavorite = true)))

        val secondContent = timetableViewModel.state.value.filteredTimetableContents
        secondContent.contents[0].first.id shouldBe favoriteContent.id
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
