package io.github.droidkaigi.feeder.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KaigiMessagingService : FirebaseMessagingService() {

    override fun onCreate() {
        NotificationUtil.createNotificationChannel(this)
    }

    override fun onNewToken(token: String) {
        // TODO: 2021/02/28 implement sending server
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO: 2021/02/28 add handling
        NotificationUtil.showNotifications()
    }
}
