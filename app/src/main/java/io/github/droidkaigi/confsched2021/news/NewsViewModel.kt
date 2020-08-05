package io.github.droidkaigi.confsched2021.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class NewsViewModel : ViewModel() {
    val articles: Articles = Articles(listOf())
}