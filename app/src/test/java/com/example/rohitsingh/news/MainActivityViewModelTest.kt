package com.example.rohitsingh.news

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.view.View
import com.example.rohitsingh.news.network.*
import com.example.rohitsingh.news.repository.ArticleModel
import com.example.rohitsingh.news.repository.NewsRepository
import com.example.rohitsingh.news.repository.SourceModel
import com.example.rohitsingh.news.repository.TopHeadlineResponse
import com.example.rohitsingh.news.ui.MainActivityViewModel
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.junit.Rule



class MainActivityViewModelTest {
    var newsRepository: NewsRepository = mock()
    lateinit var viewModel: MainActivityViewModel
    var apiResponseObserver: Observer<TopHeadlineResponse?> = mock()
    var data = MutableLiveData<ApiResponse<TopHeadlineResponse>>()

    @Rule
    @JvmField  val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        whenever(newsRepository.getTopHeadlines()).thenReturn(data)
        viewModel = MainActivityViewModel(newsRepository)
        viewModel.response.observeForever(apiResponseObserver)
    }

    @Test
    fun testIfApiIsLoading() {
        data.value = ApiIsLoading()
        assert(viewModel.showLoader.value == View.VISIBLE)
    }

    @Test
    fun testSuccessResponse() {
        data.value = ApiSuccessResponse(TopHeadlineResponse("", 0, listOf()))
        verify(apiResponseObserver).onChanged(TopHeadlineResponse("", 0, listOf()))
    }


    @Test
    fun testErrorResponse() {
        data.value = ApiErrorResponse(Exception())
        assert(viewModel.showLoader.value == View.GONE)
    }

    @Test
    fun testIfItemClicked() {
        var clickObserver: Observer<ArticleModel> = mock()
        viewModel.itemClickData.observeForever { clickObserver }
        val article = ArticleModel(SourceModel("", "") ,"", "", "", "", "", "")
        viewModel.itemClickData.value = article
        assert(argumentCaptorLiveData(viewModel.itemClickData) == article)
    }

}

private inline fun <reified T : Any> argumentCaptorLiveData(liveData: LiveData<T>): T {
    val observer = mock<Observer<T>>()
    liveData.observeForever(observer)
    val activityKArgumentCaptor = argumentCaptor<T>()
    verify(observer).onChanged(activityKArgumentCaptor.capture())
    return activityKArgumentCaptor.firstValue
}