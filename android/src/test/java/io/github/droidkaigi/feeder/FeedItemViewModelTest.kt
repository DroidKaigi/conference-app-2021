package io.github.droidkaigi.feeder

import io.github.droidkaigi.feeder.data.FeedRepositoryImpl
import io.github.droidkaigi.feeder.data.fakeFeedApi
import io.github.droidkaigi.feeder.data.fakeUserDataStore
import io.github.droidkaigi.feeder.feed.FeedViewModel
import io.github.droidkaigi.feeder.feed.FeedViewModel.Event.ChangeFavoriteFilter
import io.github.droidkaigi.feeder.feed.FeedViewModel.Event.ToggleFavorite
import io.github.droidkaigi.feeder.feed.fakeNewsViewModel
import io.github.droidkaigi.feeder.viewmodel.RealFeedViewModel
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
class FeedItemViewModelTest(
    val name: String,
    private val newsNewsViewModelFactory: NewsViewModelFactory,
) {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun contents() = coroutineTestRule.testDispatcher.runBlockingTest {
        // Replace when it fixed https://github.com/cashapp/turbine/issues/10
        val newsViewModel = newsNewsViewModelFactory.create()

        val firstContent = newsViewModel.state.value.filteredFeedContents

        firstContent.size shouldBeGreaterThan 1
    }

    @Test
    fun favorite_Add() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsNewsViewModelFactory.create()
        val firstContent = newsViewModel.state.value.filteredFeedContents
        firstContent.favorites shouldBe setOf()

        newsViewModel.event(ToggleFavorite(firstContent.feedItemContents[0]))

        val secondContent = newsViewModel.state.value.filteredFeedContents
        secondContent.favorites shouldBe setOf(firstContent.feedItemContents[0].id)
    }

    @Test
    fun favorite_Remove() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsNewsViewModelFactory.create()
        val firstContent = newsViewModel.state.value.filteredFeedContents
        firstContent.favorites shouldBe setOf()

        newsViewModel.event(ToggleFavorite(feedItem = firstContent.feedItemContents[0]))
        newsViewModel.event(ToggleFavorite(feedItem = firstContent.feedItemContents[0]))

        val secondContent = newsViewModel.state.value.filteredFeedContents
        secondContent.favorites shouldBe setOf()
    }

    @Test
    fun favorite_Filter() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsNewsViewModelFactory.create()
        val firstContent = newsViewModel.state.value.filteredFeedContents
        firstContent.favorites shouldBe setOf()
        val favoriteContents = firstContent.feedItemContents[1]

        newsViewModel.event(ToggleFavorite(feedItem = favoriteContents))
        newsViewModel.event(ChangeFavoriteFilter(Filters(filterFavorite = true)))

        val secondContent = newsViewModel.state.value.filteredFeedContents
        secondContent.contents[0].first.id shouldBe favoriteContents.id
    }

    @Test
    fun errorWhenFetch() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsNewsViewModelFactory.create(errorFetchData = true)
        val firstContent = newsViewModel.state.value.filteredFeedContents
        firstContent.favorites shouldBe setOf()

        val firstEffect = newsViewModel.effect.first()

        firstEffect.shouldBeInstanceOf<FeedViewModel.Effect.ErrorMessage>()
    }

    class NewsViewModelFactory(
        private val viewModelFactory: (errorFetchData: Boolean) ->
        FeedViewModel,
    ) {
        fun create(
            errorFetchData: Boolean = false,
        ): FeedViewModel {
            return viewModelFactory(errorFetchData)
        }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data() = listOf(
            arrayOf(
                "Real ViewModel and Repository",
                NewsViewModelFactory { errorFetchData: Boolean ->
                    RealFeedViewModel(
                        repository = FeedRepositoryImpl(
                            newsApi = fakeFeedApi(
                                if (errorFetchData) {
                                    AppError.ApiException.ServerException(null)
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
                NewsViewModelFactory { errorFetchData: Boolean ->
                    fakeNewsViewModel(errorFetchData = errorFetchData)
                }
            )
        )
    }
}
