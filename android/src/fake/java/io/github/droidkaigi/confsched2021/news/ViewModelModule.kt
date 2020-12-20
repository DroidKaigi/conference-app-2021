package io.github.droidkaigi.confsched2021.news

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.github.droidkaigi.confsched2021.news.ui.INewsViewModel
import io.github.droidkaigi.confsched2021.news.ui.fakeNewsViewModel

@InstallIn(ActivityComponent::class)
@Module
class ViewModelModule {
    @Provides
    fun provideNewsViewModel(): INewsViewModel {
        return fakeNewsViewModel()
    }
}
