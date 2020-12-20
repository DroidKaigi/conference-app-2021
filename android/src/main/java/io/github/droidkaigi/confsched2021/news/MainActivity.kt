package io.github.droidkaigi.confsched2021.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import io.github.droidkaigi.confsched2021.news.ui.DroidKaigiApp
import io.github.droidkaigi.confsched2021.news.ui.INewsViewModel
import io.github.droidkaigi.confsched2021.news.ui.ProvideNewsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: Lazy<INewsViewModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup(viewModel.get())
    }

    private fun ComponentActivity.setup(viewModel: INewsViewModel) {
        setContent {
            ProvideNewsViewModel(viewModel) {
                DroidKaigiApp()
            }
        }
    }
}
