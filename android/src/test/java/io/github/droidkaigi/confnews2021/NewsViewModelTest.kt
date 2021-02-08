package io.github.droidkaigi.confnews2021

import io.github.droidkaigi.confnews2021.data.NewsRepository
import io.github.droidkaigi.confnews2021.data.fakeNewsApi
import io.github.droidkaigi.confnews2021.data.fakeUserDataStore
import io.github.droidkaigi.confnews2021.ui.news.NewsViewModel
import io.github.droidkaigi.confnews2021.ui.news.NewsViewModel.Event.ChangeFavoriteFilter
import io.github.droidkaigi.confnews2021.ui.news.NewsViewModel.Event.ToggleFavorite
import io.github.droidkaigi.confnews2021.ui.news.fakeNewsViewModel
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
class NewsViewModelTest(
    val name: String,
    private val newsNewsViewModelFactory: NewsViewModelFactory,
) {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun contents() = coroutineTestRule.testDispatcher.runBlockingTest {
        // Replace when it fixed https://github.com/cashapp/turbine/issues/10
        val newsViewModel = newsNewsViewModelFactory.create()

        val firstContent = newsViewModel.state.value.filteredNewsContents

        firstContent.size shouldBeGreaterThan 1
    }

    @Test
    fun favorite_Add() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsNewsViewModelFactory.create()
        val firstContent = newsViewModel.state.value.filteredNewsContents
        firstContent.favorites shouldBe setOf()

        newsViewModel.event(ToggleFavorite(firstContent.newsContents[0]))

        val secondContent = newsViewModel.state.value.filteredNewsContents
        secondContent.favorites shouldBe setOf(firstContent.newsContents[0].id)
    }

    @Test
    fun favorite_Remove() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsNewsViewModelFactory.create()
        val firstContent = newsViewModel.state.value.filteredNewsContents
        firstContent.favorites shouldBe setOf()

        newsViewModel.event(ToggleFavorite(news = firstContent.newsContents[0]))
        newsViewModel.event(ToggleFavorite(news = firstContent.newsContents[0]))

        val secondContent = newsViewModel.state.value.filteredNewsContents
        secondContent.favorites shouldBe setOf()
    }

    @Test
    fun favorite_Filter() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsNewsViewModelFactory.create()
        val firstContent = newsViewModel.state.value.filteredNewsContents
        firstContent.favorites shouldBe setOf()
        val favoriteContents = firstContent.newsContents[1]

        newsViewModel.event(ToggleFavorite(news = favoriteContents))
        newsViewModel.event(ChangeFavoriteFilter(Filters(filterFavorite = true)))

        val secondContent = newsViewModel.state.value.filteredNewsContents
        secondContent.contents[0].first.id shouldBe favoriteContents.id
    }

    @Test
    fun errorWhenFetch() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsNewsViewModelFactory.create(errorFetchData = true)
        val firstContent = newsViewModel.state.value.filteredNewsContents
        firstContent.favorites shouldBe setOf()

        val firstEffect = newsViewModel.effect.first()

        firstEffect.shouldBeInstanceOf<NewsViewModel.Effect.ErrorMessage>()
    }

    class NewsViewModelFactory(
        private val viewModelFactory: (errorFetchData: Boolean) ->
        NewsViewModel,
    ) {
        fun create(
            errorFetchData: Boolean = false,
        ): NewsViewModel {
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
                    RealNewsViewModel(
                        repository = NewsRepository(
                            newsApi = fakeNewsApi(
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
