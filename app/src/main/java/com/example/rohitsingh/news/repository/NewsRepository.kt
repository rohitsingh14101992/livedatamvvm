package com.example.rohitsingh.news.repository

import android.arch.lifecycle.LiveData
import com.example.rohitsingh.news.network.ApiResponse

interface NewsRepository {
    fun getTopHeadlines(): LiveData<ApiResponse<TopHeadlineResponse>>
}