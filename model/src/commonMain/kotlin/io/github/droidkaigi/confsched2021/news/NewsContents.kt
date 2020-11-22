package io.github.droidkaigi.confsched2021.news

class NewsContents(
    private val newsContents: List<News> = listOf(),
    val favorites: Set<String> = setOf()
) {
    val contents by lazy {
        newsContents.map {
            it to favorites.contains(it.id)
        }
    }

    fun filtered(filters: Filters): NewsContents {
        var articles = newsContents.toList()
        if (filters.filterFavorite) {
            articles = articles.filter { article ->
                favorites.contains(article.id)
            }
        }
        return NewsContents(articles)
    }

    val size get() = newsContents.size
}