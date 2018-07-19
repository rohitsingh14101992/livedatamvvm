package com.example.rohitsingh.news.repository

import android.arch.lifecycle.LiveData
import com.example.rohitsingh.news.network.ApiResponse
import com.example.rohitsingh.news.network.NewsApiServices
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(val api : NewsApiServices) : NewsRepository {

    override fun getTopHeadlines(): LiveData<ApiResponse<TopHeadlineResponse>> =
            api.getTopHeadlines(country = "in", category = "technology")
}