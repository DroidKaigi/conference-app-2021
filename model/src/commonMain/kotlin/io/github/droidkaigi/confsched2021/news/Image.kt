package io.github.droidkaigi.confsched2021.news

data class Image(val url: String) {
    companion object {
        fun of(data: String): Image {
            return Image(
                if (data.startsWith("/static/")) {
                    "https://competent-aryabhata-481881.netlify.app/news/" + data
                } else {
                    data
                }
            )
        }
    }
}