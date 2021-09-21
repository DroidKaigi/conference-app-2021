package io.github.droidkaigi.feeder

data class Contributor(
    val id: Int,
    val name: String,
    val url: String,
    val image: String
)

fun fakeContributors() = listOf(
    Contributor(
        id = 1,
        name = "Pie",
        url = "https://developer.android.com/",
        image = "https://via.placeholder.com/150",
    ),
    Contributor(
        id = 2,
        name = "Oreo",
        url = "https://developer.android.com/",
        image = "https://via.placeholder.com/150",
    )
)
