package io.github.droidkaigi.feeder.core.util

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.AlarmManagerCompat
import io.github.droidkaigi.feeder.TimetableItem
import io.github.droidkaigi.feeder.core.R
import io.github.droidkaigi.feeder.defaultLang
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime

class TimetableItemAlarm @Inject constructor(private val app: Application) {

    fun toggleRegister(timetableItem: TimetableItem, isFavorite: Boolean) {
        if (isFavorite) {
            unregister(timetableItem)
        } else {
            register(timetableItem)
        }
    }

    private fun register(session: TimetableItem) {
        val time = session.startsAt.toEpochMilliseconds()

        if (System.currentTimeMillis() < time) {
            val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            AlarmManagerCompat.setAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                time,
                createAlarmIntent(session)
            )
        }
    }

    private fun unregister(session: TimetableItem) {
        val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(createAlarmIntent(session))
    }

    private fun createAlarmIntent(session: TimetableItem): PendingIntent {
        val formatter = DateTimeFormatter.ofPattern(PATTERN)
        val startsTime = session.startsAt.toLocalDateTimeWithFormatter(formatter)
        val endsTime = session.endsAt.toLocalDateTimeWithFormatter(formatter)

        val speakers = when (session) {
            is TimetableItem.Session -> session.speakers
            is TimetableItem.Special -> session.speakers
        }.joinToString(", ") { it.name }

        val sessionTitle = app.getString(
            R.string.notification_message_session_title,
            session.title.getByLang(defaultLang())
        )
        val sessionStartTime = app.getString(
            R.string.notification_message_session_start_time,
            startsTime,
            endsTime,
            speakers
        )
        val title: String
        val text: String
        // If you make this notification under Android N, the time and location will not be displayed.
        // So, under Android N, the session title is displayed in the title, the time and location are displayed in the text.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            title = app.getString(R.string.notification_channel_name_start_favorite_session)
            text = sessionTitle + "\n" + sessionStartTime
        } else {
            title = sessionTitle
            text = sessionStartTime
        }
        val intent = createNotificationIntentForSessionStart(
            app,
            session.id,
            title,
            text
        )
        return PendingIntent.getBroadcast(
            app,
            session.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createNotificationIntentForSessionStart(
        context: Context,
        sessionId: String,
        title: String,
        text: String,
    ): Intent {
        return Intent(
            context,
            Class.forName(BROADCAST_RECEIVER_CLASS_NAME)
        ).apply {
            action =
                ACTION_FAVORITED_SESSION_START
            putExtra(EXTRA_SESSION_ID, sessionId)
            putExtra(EXTRA_TITLE, title)
            putExtra(EXTRA_TEXT, text)
            putExtra(EXTRA_CHANNEL_ID, FAVORITE_SESSION_START_CHANNEL_ID)
        }
    }

    private fun Instant.toLocalDateTimeWithFormatter(formatter: DateTimeFormatter) =
        toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime().format(formatter)

    companion object {
        private const val PATTERN = "HH:mm"
        private const val BROADCAST_RECEIVER_CLASS_NAME =
            "io.github.droidkaigi.feeder.notification.NotificationBroadcastReceiver"
        private const val FAVORITE_SESSION_START_CHANNEL_ID = "FAVORITE_SESSION_START_CHANNEL"
        const val ACTION_FAVORITED_SESSION_START = "ACTION_FAVORITED_SESSION_START"
        const val EXTRA_SESSION_ID = "EXTRA_SESSION_ID"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_TEXT = "EXTRA_TEXT"
        const val EXTRA_CHANNEL_ID = "EXTRA_CHANNEL_ID"
    }
}
