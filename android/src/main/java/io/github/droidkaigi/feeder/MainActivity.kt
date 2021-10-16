package io.github.droidkaigi.feeder

import android.content.ActivityNotFoundException
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.browser.customtabs.CustomTabsIntent
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
        handleIntent()
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

    private fun handleIntent() {
        val link = intent?.data?.toString() // used when app foreground
            ?: intent?.getStringExtra("link") // used when app background
        if (link != null) {
            if (link.contains("2021/timetable/detail")) {
                // handle in Navigation Compose
                return
            }
            // Currently deeplinks are not supported
            val builder = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setUrlBarHidingEnabled(true)

            val intent = builder.build()
            try {
                intent.launchUrl(this, Uri.parse(link))
            } catch (e: ActivityNotFoundException) {
                Logger.d(e, "Fail to show $link")
            }
        }
    }

//    // for notification debug
//    override fun onResume() {
//        super.onResume()
//        sendBroadcast(
//            TimetableItemAlarm(application).createNotificationIntentForSessionStart(
//                context = application,
//                sessionId = "277266",
//                title = "title",
//                text = "text"
//            )
//        )
//    }

    private fun setup() {
        setContent {
            ProvideViewModels {
                DroidKaigiApp()
            }
        }
    }
}
