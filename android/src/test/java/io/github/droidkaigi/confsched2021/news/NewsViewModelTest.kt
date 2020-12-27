package io.github.droidkaigi.confsched2021.news

import io.github.droidkaigi.confsched2021.news.data.NewsRepository
import io.github.droidkaigi.confsched2021.news.data.fakeNewsApi
import io.github.droidkaigi.confsched2021.news.data.fakeUserDataStore
import io.github.droidkaigi.confsched2021.news.ui.news.NewsViewModel
import io.github.droidkaigi.confsched2021.news.ui.news.NewsViewModel.Event.ChangeFavoriteFilter
import io.github.droidkaigi.confsched2021.news.ui.news.NewsViewModel.Event.OpenDetail
import io.github.droidkaigi.confsched2021.news.ui.news.NewsViewModel.Event.ToggleFavorite
import io.github.droidkaigi.confsched2021.news.ui.news.fakeNewsViewModel
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
import kotlin.time.ExperimentalTime

@InternalCoroutinesApi
@OptIn(ExperimentalTime::class)
@RunWith(Parameterized::class)
class NewsViewModelTest(val name: String, val newsViewModelFactory: () -> NewsViewModel) {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun contents() = coroutineTestRule.testDispatcher.runBlockingTest {
        // Replace when it fixed https://github.com/cashapp/turbine/issues/10
        val newsViewModel = newsViewModelFactory()

        val firstContent = newsViewModel.state.value.filteredNewsContents

        firstContent.size shouldBeGreaterThan 1
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun favorite_Add() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsViewModelFactory()
        val firstContent = newsViewModel.state.value.filteredNewsContents
        firstContent.favorites shouldBe setOf()

        newsViewModel.event(ToggleFavorite(firstContent.newsContents[0]))

        val secondContent = newsViewModel.state.value.filteredNewsContents
        secondContent.favorites shouldBe setOf(firstContent.newsContents[0].id)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun favorite_Remove() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsViewModelFactory()
        val firstContent = newsViewModel.state.value.filteredNewsContents
        firstContent.favorites shouldBe setOf()

        newsViewModel.event(ToggleFavorite(news = firstContent.newsContents[0]))
        newsViewModel.event(ToggleFavorite(news = firstContent.newsContents[0]))

        val secondContent = newsViewModel.state.value.filteredNewsContents
        secondContent.favorites shouldBe setOf()
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun favorite_Filter() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsViewModelFactory()
        val firstContent = newsViewModel.state.value.filteredNewsContents
        firstContent.favorites shouldBe setOf()
        val favoriteContents = firstContent.newsContents[1]

        newsViewModel.event(ToggleFavorite(news = favoriteContents))
        newsViewModel.event(ChangeFavoriteFilter(Filters(filterFavorite = true)))

        val secondContent = newsViewModel.state.value.filteredNewsContents
        secondContent.contents[0].first.id shouldBe favoriteContents.id
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun openDetail() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsViewModelFactory()
        val firstContent = newsViewModel.state.value.filteredNewsContents
        firstContent.favorites shouldBe setOf()
        val news = firstContent.newsContents[1]

        newsViewModel.event(OpenDetail(news = news))

        val firstEffect = newsViewModel.effect.first()
        firstEffect.shouldBeInstanceOf<NewsViewModel.Effect.OpenDetail>()
        firstEffect.news shouldBe news
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data() = listOf(
            arrayOf(
                "Real ViewModel and Repository",
                {
                    RealNewsViewModel(
                        repository = NewsRepository(fakeNewsApi(), fakeUserDataStore())
                    )
                }
            ),
            arrayOf(
                "FakeViewModel",
                {
                    fakeNewsViewModel()
                }
            )
        )
    }
}
