package io.github.droidkaigi.feeder.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import io.github.droidkaigi.feeder.repository.DeviceRepository
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var deviceRepository: DeviceRepository

    private val serviceJob: Job = Job()

    private val serviceScope: CoroutineScope = CoroutineScope(Dispatchers.IO + serviceJob)

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
        // TODO: 2021/02/28 add handling
        NotificationUtil.showNotifications()
    }

    override fun onDestroy() {
        serviceJob.cancel()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "AppMessagingService"
    }
}
