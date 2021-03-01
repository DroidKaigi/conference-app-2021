package io.github.droidkaigi.feeder

import io.github.droidkaigi.feeder.core.ContributorViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@InternalCoroutinesApi
@RunWith(Parameterized::class)
class ContributorViewModelTest {

    class ContributorViewModelFactory(
        private val viewModelFactory: (errorFetchData: Boolean) -> ContributorViewModel
    ) {
        fun create(errorFetchData: Boolean = false) = viewModelFactory(errorFetchData)
    }

}
