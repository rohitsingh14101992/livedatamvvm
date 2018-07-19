package com.example.rohitsingh.news.network

import android.arch.lifecycle.LiveData
import com.example.rohitsingh.news.repository.TopHeadlineResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiServices {

    @GET("top-headlines")
    fun getTopHeadlines(@Query("country") country: String,
                        @Query("category") category: String
    ): LiveData<ApiResponse<TopHeadlineResponse>>


}