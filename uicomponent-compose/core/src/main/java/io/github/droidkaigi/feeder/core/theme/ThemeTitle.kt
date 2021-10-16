package io.github.droidkaigi.feeder.core.theme

import androidx.compose.runtime.Composable
import io.github.droidkaigi.feeder.MultiLangText
import io.github.droidkaigi.feeder.Theme
import io.github.droidkaigi.feeder.core.R
import io.github.droidkaigi.feeder.core.language.from
import io.github.droidkaigi.feeder.core.language.getTextWithSetting

@Composable
fun Theme?.getTitle(): String {
    val res = when (this) {
        Theme.SYSTEM -> R.string.system
        Theme.DARK -> R.string.dark
        Theme.LIGHT -> R.string.light
        else -> R.string.system
    }
    return MultiLangText.from(res).getTextWithSetting()
}
