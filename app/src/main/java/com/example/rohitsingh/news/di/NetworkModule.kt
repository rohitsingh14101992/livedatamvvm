package com.example.rohitsingh.news.di

import com.example.rohitsingh.news.network.LiveDataCallAdapter
import com.example.rohitsingh.news.network.LiveDataCallAdapterFactory
import com.example.rohitsingh.news.network.NewsApiServices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val API_KEY = "730d73941b354fda91a65067188fbd7b"

@Module
class NetworkModule {

    @Provides
    fun provideNewsApi(retrofit: Retrofit) = retrofit.create(NewsApiServices::class.java)

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) =
            Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()


    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder().addHeader("X-Api-Key", API_KEY).build()
                    chain.proceed(request)
                }
                .build()
    }
}