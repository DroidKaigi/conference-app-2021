package io.github.droidkaigi.confsched2021.news

import kotlinx.coroutines.flow.Flow

interface INewsViewModel {
    val articles: Flow<Articles>
}