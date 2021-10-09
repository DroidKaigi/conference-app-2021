package io.github.droidkaigi.feeder.core.language

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import io.github.droidkaigi.feeder.Lang
import io.github.droidkaigi.feeder.MultiLangText
import java.util.Locale

@Composable
fun MultiLangText.getTextWithSetting(): String {
    return when (val lang = LocalLangSetting.current) {
        Lang.SYSTEM -> currentLangTitle
        else -> getByLang(lang)
    }
}

@Composable
fun MultiLangText.Companion.from(
    @StringRes res: Int,
): MultiLangText {
    val context = LocalContext.current
    val jaConfiguration = Configuration(context.resources.configuration).apply {
        setLocale(Locale.JAPAN)
    }
    val enConfiguration = Configuration(context.resources.configuration).apply {
        setLocale(Locale.ENGLISH)
    }
    return MultiLangText(
        jaTitle = context.createConfigurationContext(jaConfiguration).getString(res),
        enTitle = context.createConfigurationContext(enConfiguration).getString(res),
    )
}
