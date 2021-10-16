package io.github.droidkaigi.feeder.core.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import io.github.droidkaigi.feeder.Lang

val LocalLangSetting = LangSettingProvidableCompositionLocal()

@Composable
fun ProvideLangSetting(language: Lang?, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLangSetting provides language) {
        content()
    }
}

@JvmInline
value class LangSettingProvidableCompositionLocal internal constructor(
    private val delegate: ProvidableCompositionLocal<Lang?> = compositionLocalOf { null },
) {
    val current: Lang
        @Composable get() = delegate.current ?: Lang.SYSTEM

    infix fun provides(value: Lang?) = delegate provides value

    infix fun providesDefault(value: Lang?) = delegate providesDefault value
}
