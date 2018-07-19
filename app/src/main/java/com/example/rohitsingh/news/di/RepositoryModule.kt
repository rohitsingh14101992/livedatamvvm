package com.example.rohitsingh.news.di

import com.example.rohitsingh.news.network.NewsApiServices
import com.example.rohitsingh.news.repository.NewsRepository
import com.example.rohitsingh.news.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun getNewsRepo(api: NewsApiServices) : NewsRepository = NewsRepositoryImpl(api)

}