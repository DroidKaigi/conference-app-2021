package io.github.droidkaigi.confsched2021.news

class Articles(private val articles: List<Article> = listOf()) {
    val size get() = articles.size
    val allArticles get() = articles

    val bigArticle get() = articles.first()
    val remainArticles get() = articles.drop(1)
}