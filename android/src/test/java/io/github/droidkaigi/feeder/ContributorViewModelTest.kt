package io.github.droidkaigi.feeder

import io.github.droidkaigi.feeder.core.ContributorViewModel
import io.github.droidkaigi.feeder.core.fakeContributorViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Rule
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
                "FakeViewModel",
                ContributorViewModelFactory { errorFetchData ->
                    fakeContributorViewModel(errorFetchData)
                }
            )
        )
    }

}
