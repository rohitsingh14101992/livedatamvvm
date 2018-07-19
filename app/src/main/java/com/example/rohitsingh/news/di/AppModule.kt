package com.example.rohitsingh.news.di

import dagger.Module

@Module(includes = [NetworkModule::class, RepositoryModule::class])
class AppModule {

}