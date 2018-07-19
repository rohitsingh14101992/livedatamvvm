package com.example.rohitsingh.news

import android.app.Activity
import android.app.Application
import com.example.rohitsingh.news.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class NewsApp : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.
                builder().create(this).inject(this)
    }
}