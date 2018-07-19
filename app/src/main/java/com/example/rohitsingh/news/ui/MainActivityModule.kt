package com.example.rohitsingh.news.ui

import com.example.rohitsingh.news.repository.NewsRepository
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    internal fun provideViewModelFactory(repository: NewsRepository) : MainActivityViewModelFactory
            = MainActivityViewModelFactory(repository)
}