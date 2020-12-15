package io.github.droidkaigi.confsched2021.news

import com.soywiz.klock.DateTimeTz
import kotlin.reflect.KClass

data class NewsContents(
    val newsContents: List<News> = listOf(),
    val favorites: Set<String> = setOf()
) {
    val contents by lazy {
        newsContents.map {
            it to favorites.contains(it.id)
        }
    }

    fun filtered(filters: Filters): NewsContents {
        var news = newsContents.toList()
        if (filters.filterFavorite) {
            news = news.filter { news ->
                favorites.contains(news.id)
            }
        }
        return copy(newsContents = news)
    }

    fun filterNewsType(newsClass: KClass<out News>): NewsContents {
        return copy(newsContents = newsContents.filter { it::class == newsClass })
    }

    val size get() = newsContents.size
}

fun fakeNewsContents(): NewsContents {
    return NewsContents(
        newsContents = listOf(
            News.Blog(
                "2020-07-22-droidkaigi2020",
                DateTimeTz.nowLocal(),
                "collection",
                Image(""),
                "Medium",
                LocaledContents(
                    mapOf(
                        Locale("ja") to LocaledContents.Contents(
                            "DroidKaigi Blog!!",
                            "link-ja"
                        ),
                        Locale("en") to LocaledContents.Contents(
                            "title-en",
                            "link-en"
                        )
                    )
                )
            ),
            News.Video(
                "youtube id 1",
                DateTimeTz.nowLocal(),
                "collection",
                Image(""),
                "Medium",
                LocaledContents(
                    mapOf(
                        Locale("ja") to LocaledContents.Contents(
                            "DroidKaigi YouTube!!",
                            "link-ja"
                        ),
                        Locale("en") to LocaledContents.Contents(
                            "title-en",
                            "link-en"
                        )
                    )
                )
            )
        ),
        favorites = setOf()
    )
}
