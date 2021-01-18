package io.github.droidkaigi.confsched2021.news

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import io.github.droidkaigi.confsched2021.news.ui.DroidKaigiApp
import io.github.droidkaigi.confsched2021.news.ui.news.NewsViewModel
import io.github.droidkaigi.confsched2021.news.ui.news.ProvideNewsViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val newsViewModel by viewModels<RealNewsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setup(newsViewModel)
    }

    private fun setup(viewModel: NewsViewModel) {
        setContent {
            ProvideNewsViewModel(viewModel) {
                DroidKaigiApp()
            }
        }
    }
}
