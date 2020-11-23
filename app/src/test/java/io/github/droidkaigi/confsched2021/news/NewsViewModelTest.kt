package io.github.droidkaigi.confsched2021.news

import io.github.droidkaigi.confsched2021.news.data.ArticleRepository
import io.github.droidkaigi.confsched2021.news.data.fakeNewsApi
import io.github.droidkaigi.confsched2021.news.data.fakeUserDataStore
import io.github.droidkaigi.confsched2021.news.ui.INewsViewModel
import io.github.droidkaigi.confsched2021.news.ui.fakeNewsViewModel
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.time.ExperimentalTime

@InternalCoroutinesApi
@OptIn(ExperimentalTime::class)
@RunWith(Parameterized::class)
class NewsViewModelTest(val name: String, val newsViewModelFactory: () -> INewsViewModel) {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun contents() = coroutineTestRule.testDispatcher.runBlockingTest {
        // Replace when it fixed https://github.com/cashapp/turbine/issues/10
        val newsViewModel = newsViewModelFactory()

        val firstContent = newsViewModel.newsContents.value

        firstContent.size shouldBeGreaterThan 1

    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun favorite_Add() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsViewModelFactory()
        val firstContent = newsViewModel.newsContents.value
        firstContent.favorites shouldBe setOf()

        newsViewModel.onToggleFavorite(fakeNewsContents().newsContents[0])

        val secondContent = newsViewModel.newsContents.value
        secondContent.favorites shouldBe setOf(fakeNewsContents().newsContents[0].id)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun favorite_Remove() = coroutineTestRule.testDispatcher.runBlockingTest {
        val newsViewModel = newsViewModelFactory()
        val firstContent = newsViewModel.newsContents.value
        firstContent.favorites shouldBe setOf()

        newsViewModel.onToggleFavorite(fakeNewsContents().newsContents[0])
        newsViewModel.onToggleFavorite(fakeNewsContents().newsContents[0])

        val secondContent = newsViewModel.newsContents.value
        secondContent.favorites shouldBe setOf()
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data() = listOf(
            arrayOf("Real ViewModel and Repository", {
                NewsViewModel(
                    repository = ArticleRepository(fakeNewsApi(), fakeUserDataStore())
                )
            }),
            arrayOf("FakeViewModel", {
                fakeNewsViewModel()
            })
        )
    }
}
