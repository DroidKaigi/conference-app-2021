package io.github.droidkaigi.confsched2021.news

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.droidkaigi.confsched2021.news.data.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModel @ViewModelInject constructor(api: Api) : ViewModel(), INewsViewModel {
    private val allArticles: MutableStateFlow<Articles> = MutableStateFlow(Articles())
    override val filter: MutableStateFlow<Filters> = MutableStateFlow(Filters())
    override val articles: StateFlow<Articles> = allArticles
        .combine(filter) { articles, filters ->
            articles.filtered(filters)
        }
        .toStateFlow(viewModelScope, Articles())

    init {
        viewModelScope.launch {
            allArticles.value = api.fetch()
        }
    }

    override fun onFilterChanged(filters: Filters) {
        filter.value = filters
    }
}

// FIXME: replace when it released https://github.com/Kotlin/kotlinx.coroutines/pull/2069
@Suppress("NOTHING_TO_INLINE")
@OptIn(ExperimentalCoroutinesApi::class)
private inline fun <T> Flow<T>.toStateFlow(scope: CoroutineScope, initialValue: T): StateFlow<T> {
    val mutableStateFlow = MutableStateFlow(initialValue)
    onEach { mutableStateFlow.value = it }.launchIn(scope)
    return mutableStateFlow
}
