package io.github.droidkaigi.confsched2021.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ambientOf
import kotlinx.coroutines.flow.Flow

interface INewsViewModel {
    val articles: Flow<Articles>
}

val NewsViewModelAmbient = ambientOf<INewsViewModel>()

@Composable
fun newsViewModel() = NewsViewModelAmbient.current
