package com.example.rohitsingh.news.di

import android.app.Application
import com.example.rohitsingh.news.NewsApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class, ActivityBuilder::class])
interface AppComponent : AndroidInjector<NewsApp> {
    @Component.Builder
    abstract  class Builder : AndroidInjector.Builder<NewsApp>()

}