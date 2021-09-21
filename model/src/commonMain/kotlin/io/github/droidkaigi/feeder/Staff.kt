package io.github.droidkaigi.feeder

class Staff(
    val id: Int,
    val username: String,
    val profileUrl: String,
    val iconUrl: String,
)

fun fakeStaffs() = listOf(
    Staff(
        id = 15,
        username = "Cupcake",
        profileUrl = "https://developer.android.com/",
        iconUrl = "https://via.placeholder.com/150",
    ),
    Staff(
        id = 16,
        username = "Doughnut",
        profileUrl = "https://developer.android.com/",
        iconUrl = "https://via.placeholder.com/150",
    )
)
