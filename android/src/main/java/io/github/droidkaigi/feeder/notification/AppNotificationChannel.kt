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
    ANNOUNCEMENT(
        "announcement",
        R.string.notification_channel_name_announcement,
        NotificationManagerCompat.IMPORTANCE_DEFAULT
    ),
    BLOG(
        "blog",
        R.string.notification_channel_name_blog,
        NotificationManagerCompat.IMPORTANCE_DEFAULT
    ),
    VIDEO(
        "video",
        R.string.notification_channel_name_video,
        NotificationManagerCompat.IMPORTANCE_DEFAULT
    ),
    PODCAST(
        "podcast",
        R.string.notification_channel_name_podcast,
        NotificationManagerCompat.IMPORTANCE_DEFAULT
    );

    fun channelName(context: Context) = context.getString(channelName)

    companion object {
        @JvmStatic
        fun fromId(id: String): AppNotificationChannel {
            return values().find { it.id == id } ?: ANNOUNCEMENT
        }
    }
}
