package io.github.droidkaigi.feeder.core.theme

import android.content.Context
import io.github.droidkaigi.feeder.Theme
import io.github.droidkaigi.feeder.core.R

fun Theme.getTitle(context: Context): String = when (this) {
    Theme.SYSTEM -> context.getString(R.string.system)
    Theme.BATTERY -> context.getString(R.string.battery)
    Theme.DARK -> context.getString(R.string.dark)
    Theme.LIGHT -> context.getString(R.string.light)
    else -> context.getString(R.string.error_unknown)
}
