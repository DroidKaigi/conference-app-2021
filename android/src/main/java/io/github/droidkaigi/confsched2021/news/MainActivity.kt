package io.github.droidkaigi.confsched2021.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import io.github.droidkaigi.confsched2021.news.ui.DroidKaigiApp
import io.github.droidkaigi.confsched2021.news.ui.NewsViewModel
import io.github.droidkaigi.confsched2021.news.ui.ProvideNewsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var newsViewModel: Lazy<NewsViewModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup(newsViewModel.get())
    }

    private fun setup(viewModel: NewsViewModel) {
        setContent {
            ProvideNewsViewModel(viewModel) {
                DroidKaigiApp()
            }
        }
    }

}
