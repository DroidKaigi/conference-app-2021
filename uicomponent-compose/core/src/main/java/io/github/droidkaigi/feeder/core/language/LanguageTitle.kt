package io.github.droidkaigi.feeder.core.language

import androidx.compose.runtime.Composable
import io.github.droidkaigi.feeder.Lang
import io.github.droidkaigi.feeder.MultiLangText
import io.github.droidkaigi.feeder.core.R

@Composable
fun Lang?.getTitle(): String {
    val res = when (this) {
        Lang.SYSTEM -> R.string.system
        Lang.JA -> R.string.ja
        Lang.EN -> R.string.en
        else -> R.string.system
    }
    return MultiLangText.from(res).getTextWithSetting()
}
