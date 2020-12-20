package io.github.droidkaigi.confsched2021.news

import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.github.droidkaigi.confsched2021.news.data.NewsRepository
import io.github.droidkaigi.confsched2021.news.ui.INewsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@InstallIn(ActivityComponent::class)
@Module
class ViewModelModule {
    @Provides
    fun provideNewsViewModel(fragmentActivity: FragmentActivity): INewsViewModel {
        return fragmentActivity.viewModels<RealNewsViewModel>().value
    }
}

class RealNewsViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel(), INewsViewModel {

    private val allNewsContents: Flow<NewsContents> = repository.newsContents()
    override val filters: MutableStateFlow<Filters> = MutableStateFlow(Filters())
    override val filteredNewsContents: StateFlow<NewsContents> = allNewsContents
        .combine(filters) { newsContents, filters ->
            newsContents.filtered(filters)
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, NewsContents())

    override fun onToggleFavorite(news: News) {
        viewModelScope.launch {
            if (filteredNewsContents.value.favorites.contains(news.id)) {
                repository.removeFavorite(news)
            } else {
                repository.addFavorite(news)
            }
        }
    }

    override fun onFilterChanged(filters: Filters) {
        this.filters.value = filters
    }
}
