package com.example.rohitsingh.news.di

import com.example.rohitsingh.news.ui.MainActivity
import com.example.rohitsingh.news.ui.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity
}