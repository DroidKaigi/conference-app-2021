package io.github.droidkaigi.feeder.core.language

import android.content.Context
import io.github.droidkaigi.feeder.Lang
import io.github.droidkaigi.feeder.core.R

fun Lang?.getTitle(context: Context): String = when (this) {
    Lang.SYSTEM -> context.getString(R.string.system)
    Lang.JA -> context.getString(R.string.ja)
    Lang.EN -> context.getString(R.string.en)
    else -> context.getString(R.string.system)
}
