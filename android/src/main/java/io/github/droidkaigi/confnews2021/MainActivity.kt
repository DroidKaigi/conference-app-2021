package io.github.droidkaigi.confnews2021

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import io.github.droidkaigi.confnews2021.staff.DroidKaigiApp
import io.github.droidkaigi.confnews2021.viewmodel.ProvideViewModels

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
