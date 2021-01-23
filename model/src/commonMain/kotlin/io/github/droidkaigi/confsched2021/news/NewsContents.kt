package io.github.droidkaigi.confsched2021.news

import kotlinx.datetime.Clock
import kotlin.reflect.KClass

data class NewsContents(
    val newsContents: List<News> = listOf(),
    val favorites: Set<String> = setOf(),
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

fun NewsContents?.orEmptyContents(): NewsContents = this ?: NewsContents()
fun LoadState<NewsContents>.getContents() = getValueOrNull() ?: NewsContents()

fun fakeNewsContents(): NewsContents {
    return NewsContents(
        newsContents = listOf(
//            News.Blog(
//                "2020-07-22-droidkaigi2020",
//                Clock.System.now(),
//                "collection",
//                Image(""),
//                "Medium",
//                MultiLangText(
//                    mapOf(
//                        Locale("ja") to MultiLangText.Contents(
//                            "DroidKaigi Blog!!",
//                            "link-ja"
//                        ),
//                        Locale("en") to MultiLangText.Contents(
//                            "title-en",
//                            "link-en"
//                        )
//                    )
//                ),
//                article.link,
//                article.language,
//                Author(
//                    name = article.authorName,
//                    link = article.authorUrl
//                )
//            ),
//            News.Video(
//                "youtube id 1",
//                Clock.System.now(),
//                "collection",
//                Image(""),
//                "Medium",
//                MultiLangText(
//                    mapOf(
//                        Locale("ja") to MultiLangText.Contents(
//                            "DroidKaigi YouTube!!",
//                            "link-ja"
//                        ),
//                        Locale("en") to MultiLangText.Contents(
//                            "title-en",
//                            "link-en"
//                        )
//                    )
//                )
//            )
        ),
        favorites = setOf()
    )
}
