package io.github.droidkaigi.confsched2021.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import dagger.hilt.android.AndroidEntryPoint
import io.github.droidkaigi.confsched2021.news.ui.INewsViewModel
import io.github.droidkaigi.confsched2021.news.ui.NewsApp
import io.github.droidkaigi.confsched2021.news.ui.ProvideNewsViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<NewsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup(viewModel)
    }

    private fun ComponentActivity.setup(viewModel: INewsViewModel) {
        setContent {
            ProvideNewsViewModel(viewModel) {
                NewsApp()
            }
        }
    }
}