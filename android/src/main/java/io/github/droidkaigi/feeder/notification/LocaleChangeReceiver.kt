package io.github.droidkaigi.feeder.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class LocaleChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            intent.action == Intent.ACTION_LOCALE_CHANGED
        ) {
            NotificationUtil.deleteNotificationChannel(context)
            NotificationUtil.createNotificationChannel(context)
        }
    }
}
