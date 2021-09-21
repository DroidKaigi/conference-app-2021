package io.github.droidkaigi.feeder

data class MultiLangText(
    val jaTitle: String,
    val enTitle: String,
) {
    val currentLangTitle get() = getByLang(defaultLang())

    fun getByLang(lang: Lang): String {
        return if (lang == Lang.JA) {
            jaTitle
        } else {
            enTitle
        }
    }
}
