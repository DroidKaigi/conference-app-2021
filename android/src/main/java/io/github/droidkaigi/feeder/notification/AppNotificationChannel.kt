package io.github.droidkaigi.feeder.notification

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.app.NotificationManagerCompat
import io.github.droidkaigi.feeder.R

enum class AppNotificationChannel(
    val id: String,
    @StringRes val channelName: Int,
    val importance: Int,
) {
    DEFAULT(
        "default_channel",
        R.string.notification_channel_name_default,
        NotificationManagerCompat.IMPORTANCE_DEFAULT
    ),

    ANNOUNCEMENT(
        "announcement",
        R.string.notification_channel_name_announcement,
        NotificationManagerCompat.IMPORTANCE_DEFAULT
    );

    fun channelName(context: Context) = context.getString(channelName)

    companion object {
        @JvmStatic
        fun fromId(id: String): AppNotificationChannel {
            return values().find { it.id == id } ?: DEFAULT
        }
    }
}
