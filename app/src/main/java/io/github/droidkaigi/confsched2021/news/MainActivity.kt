package io.github.droidkaigi.confsched2021.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Providers
import androidx.compose.ui.platform.setContent

class MainActivity : AppCompatActivity() {
    val viewModel by viewModels<NewsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup(viewModel)
    }

    fun ComponentActivity.setup(viewModel: INewsViewModel) {
        setContent {
            Providers(NewsViewModelAmbient provides viewModel) {
                NewsApp()
            }
        }
    }
}