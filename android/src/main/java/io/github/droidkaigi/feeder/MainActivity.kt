package io.github.droidkaigi.feeder

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import io.github.droidkaigi.feeder.viewmodel.ProvideViewModels

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)
        setup()
    }

    private fun setup() {
        setContent {
            ProvideViewModels {
                DroidKaigiApp()
            }
        }
    }
}
