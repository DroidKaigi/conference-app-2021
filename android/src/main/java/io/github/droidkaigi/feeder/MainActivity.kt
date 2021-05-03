package io.github.droidkaigi.feeder

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.github.droidkaigi.feeder.viewmodel.ProvideViewModels
import io.github.droidkaigi.feeder.viewmodel.RealAppViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val appViewModel by viewModels<RealAppViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setup()
        appViewModel.state
            .onEach {
                AppCompatDelegate.setDefaultNightMode(
                    when (it.theme) {
                        Theme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                        Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                        null -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    }
                )
            }
            .launchIn(lifecycleScope)
    }

    private fun setup() {
        setContent {
            ProvideViewModels {
                DroidKaigiApp()
            }
        }
    }
}
