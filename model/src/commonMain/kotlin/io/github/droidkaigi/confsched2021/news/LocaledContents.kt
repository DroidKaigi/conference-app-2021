package io.github.droidkaigi.confsched2021.news

class LocaledContents(private val contents: Map<Locale, Contents>) {
    init {
        require(contents.isNotEmpty())
    }

    fun getContents(locale: Locale) = contents[locale] ?: contents.values.first()
    class Contents(
        val title: String,
        val link: String
    )
}