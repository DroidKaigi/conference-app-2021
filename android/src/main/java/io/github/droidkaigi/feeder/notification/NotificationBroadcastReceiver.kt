package io.github.droidkaigi.feeder.notification

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import io.github.droidkaigi.feeder.MainActivity
import io.github.droidkaigi.feeder.core.R
import io.github.droidkaigi.feeder.core.util.SessionAlarm.Companion.EXTRA_CHANNEL_ID
import io.github.droidkaigi.feeder.core.util.SessionAlarm.Companion.EXTRA_SESSION_ID
import io.github.droidkaigi.feeder.core.util.SessionAlarm.Companion.EXTRA_TEXT
import io.github.droidkaigi.feeder.core.util.SessionAlarm.Companion.EXTRA_TITLE

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
            createDeepLink(context, sessionId).toUri(),
            context,
            MainActivity::class.java
        )
        val deepLinkPendingIntent: PendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(sessionDetailIntent)
            getPendingIntent(sessionId.hashCode(), PendingIntent.FLAG_UPDATE_CURRENT)
        }
        NotificationUtil.showSessionNotification(context,
            title,
            text,
            deepLinkPendingIntent,
            channelId)
    }

    private fun createDeepLink(context: Context, id: String): String {
        return "https://" + context.getString(R.string.deep_link_host) +
            context.getString(R.string.deep_link_path) + "/timetable/detail/${id}"
    }
}
