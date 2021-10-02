package io.github.droidkaigi.feeder.notification

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import io.github.droidkaigi.feeder.MainActivity
import io.github.droidkaigi.feeder.core.util.TimetableItemAlarm.Companion.EXTRA_CHANNEL_ID
import io.github.droidkaigi.feeder.core.util.TimetableItemAlarm.Companion.EXTRA_SESSION_ID
import io.github.droidkaigi.feeder.core.util.TimetableItemAlarm.Companion.EXTRA_TEXT
import io.github.droidkaigi.feeder.core.util.TimetableItemAlarm.Companion.EXTRA_TITLE

class NotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return
        val sessionId = intent.getStringExtra(EXTRA_SESSION_ID) ?: ""
        val title = intent.getStringExtra(EXTRA_TITLE) ?: ""
        val text = intent.getStringExtra(EXTRA_TEXT) ?: ""
        val channelId = intent.getStringExtra(EXTRA_CHANNEL_ID) ?: ""
        val sessionDetailIntent = Intent(
            Intent.ACTION_VIEW,
            createDeepLink(sessionId),
            context,
            MainActivity::class.java
        )
        TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(sessionDetailIntent)
            getPendingIntent(sessionId.hashCode(), PendingIntent.FLAG_UPDATE_CURRENT)
        }?.let {
            NotificationUtil.showTimetableItemNotification(
                context,
                title,
                text,
                it,
                channelId
            )
        }
    }

    private fun createDeepLink(id: String) =
        "https://droidkaigi.jp/2021/timetable/detail/$id".toUri()
}
