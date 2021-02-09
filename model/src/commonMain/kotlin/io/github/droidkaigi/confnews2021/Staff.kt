package io.github.droidkaigi.confnews2021

class Staff(
    val id: String,
    val name: String,
    val url: String,
    val image: String,
)

fun fakeStaffs() = listOf(
    Staff(
        id = "1.5",
        name = "Cupcake",
        url = "https://developer.android.com/",
        image = "https://via.placeholder.com/150",
    ),
    Staff(
        id = "1.6",
        name = "Doughnut",
        url = "https://developer.android.com/",
        image = "https://via.placeholder.com/150",
    )
)
