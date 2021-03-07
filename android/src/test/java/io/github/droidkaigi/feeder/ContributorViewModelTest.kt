package io.github.droidkaigi.feeder

import io.github.droidkaigi.feeder.contributor.ContributorViewModel
import io.github.droidkaigi.feeder.contributor.fakeContributorViewModel
import io.github.droidkaigi.feeder.data.ContributorRepositoryImpl
import io.github.droidkaigi.feeder.data.fakeContributorApi
import io.github.droidkaigi.feeder.viewmodel.RealContributorViewModel
import io.kotest.matchers.ints.shouldBeGreaterThan
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
class ContributorViewModelTest(
    val name: String,
    private val contributorViewModelFactory: ContributorViewModelFactory
) {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun contents() = coroutineTestRule.testDispatcher.runBlockingTest {
        val contributorViewModel = contributorViewModelFactory.create()
        contributorViewModel.state.value.contributorContents.size shouldBeGreaterThan 1
    }

    @Test
    fun errorWhenFetch() = coroutineTestRule.testDispatcher.runBlockingTest {
        val contributorViewModel = contributorViewModelFactory.create(errorFetchData = true)
        contributorViewModel.effect.first()
            .shouldBeInstanceOf<ContributorViewModel.Effect.ErrorMessage>()
    }

    class ContributorViewModelFactory(
        private val viewModelFactory: (errorFetchData: Boolean) -> ContributorViewModel
    ) {
        fun create(errorFetchData: Boolean = false) = viewModelFactory(errorFetchData)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data() = listOf(
            arrayOf(
                "Real ViewModel and Repository",
                ContributorViewModelFactory { errorFetchData: Boolean ->
                    RealContributorViewModel(
                        contributorRepository = ContributorRepositoryImpl(
                            contributorApi = fakeContributorApi(
                                if (errorFetchData) {
                                    AppError.ApiException.ServerException(null)
                                } else {
                                    null
                                }
                            )
                        )
                    )
                }
            ),
            arrayOf(
                "FakeViewModel",
                ContributorViewModelFactory { errorFetchData ->
                    fakeContributorViewModel(errorFetchData)
                }
            )
        )
    }
}
