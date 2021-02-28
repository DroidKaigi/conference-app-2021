package io.github.droidkaigi.feeder.notification

import android.content.Context
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtil {
    @JvmStatic
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = NotificationManagerCompat.from(context)
            for (notificationChannel in KaigiNotificationChannel.values()) {
                manager.createNotificationChannel(
                    NotificationChannelCompat
                        .Builder(
                            notificationChannel.id,
                            notificationChannel.importance
                        )
                        .setName(
                            notificationChannel.channelName(context)
                        )
                        .build()
                )
            }
        }
    }

    @JvmStatic
    fun deleteNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = NotificationManagerCompat.from(context)
            for (notificationChannelId in KaigiNotificationChannel.values().map { it.id }) {
                manager.deleteNotificationChannel(notificationChannelId)
            }
        }
    }

    @JvmStatic
    fun showNotifications() {
        // TODO: 2021/02/28 add implementation
    }
}
