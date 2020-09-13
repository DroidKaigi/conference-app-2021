package io.github.droidkaigi.confsched2021.news

class Articles(private val articles: List<Article> = listOf()) {
    fun filtered(filters: Filters): Articles {
        var articles = articles.toList()
        if (filters.filterFavorite) {
            articles = articles.filter { article ->
                article.isFavorited == filters.filterFavorite
            }
        }
        return Articles(articles)
    }

    val size get() = articles.size
    val allArticles get() = articles

    val bigArticle get() = articles.first()
    val remainArticles get() = articles.drop(1)
}