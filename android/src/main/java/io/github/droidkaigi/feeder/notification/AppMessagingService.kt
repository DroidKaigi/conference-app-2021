package io.github.droidkaigi.feeder.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import io.github.droidkaigi.feeder.repository.DeviceRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var deviceRepository: DeviceRepository

    private val serviceScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        NotificationUtil.createNotificationChannel(this)
    }

    override fun onNewToken(token: String) {
        serviceScope.launch {
            runCatching {
                deviceRepository.updateDeviceToken(token)
            }.onFailure {
                Log.e(TAG, "Failed to register device token", it)
            }
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        runCatching {
            remoteMessage.notification?.let {
                NotificationUtil.showNotifications(this, it, remoteMessage.data)
            } ?: Log.w(TAG, "notification not found")
        }.onFailure {
            Log.e(TAG, "notification failed", it)
        }
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "AppMessagingService"
    }
}
