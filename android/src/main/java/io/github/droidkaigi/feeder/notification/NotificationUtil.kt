package io.github.droidkaigi.feeder.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import com.google.firebase.messaging.RemoteMessage
import io.github.droidkaigi.feeder.MainActivity
import io.github.droidkaigi.feeder.main.R as MainR

object NotificationUtil {
    @JvmStatic
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = NotificationManagerCompat.from(context)
            for (notificationChannel in AppNotificationChannel.values()) {
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
            for (notificationChannelId in AppNotificationChannel.values().map { it.id }) {
                manager.deleteNotificationChannel(notificationChannelId)
            }
        }
    }

    @JvmStatic
    fun showNotifications(
        context: Context,
        remoteNotification: RemoteMessage.Notification,
        data: Map<String, String>,
    ) {
        val channel = AppNotificationChannel.fromId(remoteNotification.channelId ?: "")
        val manager = NotificationManagerCompat.from(context)
        val notification = NotificationCompat.Builder(context, channel.id)
            .setSmallIcon(MainR.drawable.ic_logo)
            .setContentTitle(remoteNotification.title)
            .setContentText(remoteNotification.body)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .setBigContentTitle(remoteNotification.title)
                    .bigText(remoteNotification.body)
            )
            .setContentIntent(
                getPendingIntent(
                    context,
                    remoteNotification.link,
                    data
                )
            )
            .build()
        manager.notify(channel.id.hashCode(), notification)
    }

    @JvmStatic
    private fun getPendingIntent(
        context: Context,
        link: Uri?,
        data: Map<String, Any>,
    ): PendingIntent {
        val options = bundleOf(*data.map { it.key to it.value }.toTypedArray())
        val deepLink = link ?: (data["link"] as? String)?.let { Uri.parse(it) }

        val intent = if (deepLink != null) {
            Intent(Intent.ACTION_VIEW, deepLink, context, MainActivity::class.java)
        } else {
            Intent(context, MainActivity::class.java)
        }

        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT,
            options
        )
    }
}
