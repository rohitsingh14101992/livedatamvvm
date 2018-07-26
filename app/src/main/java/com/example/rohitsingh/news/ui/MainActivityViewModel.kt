package com.example.rohitsingh.news.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.rohitsingh.news.SingleLiveEvent
import com.example.rohitsingh.news.repository.ArticleModel
import com.example.rohitsingh.news.repository.NewsRepository
import com.example.rohitsingh.news.repository.TopHeadlineResponse
import com.example.rohitsingh.news.switchMapForApiResponse
import java.util.ArrayList

class MainActivityViewModel constructor (val newsRepository: NewsRepository) : ViewModel() {

    val showLoader = MutableLiveData<Int>()
    var response: LiveData<TopHeadlineResponse?>
    val loadData : SingleLiveEvent<Unit> = SingleLiveEvent()
    val itemClickData: SingleLiveEvent<ArticleModel> = SingleLiveEvent()
    val clickListener = ObservableField<ItemClickListener>()

    init {
        response = Transformations.switchMap(loadData){
            switchMapForApiResponse(newsRepository.getTopHeadlines(), doOnSuccess = {
                showLoader.value = View.GONE
                return@switchMapForApiResponse it
            }, doOnSubscribe = {
                showLoader.value = View.VISIBLE
            }, doOnError = {
                showLoader.value = View.GONE
            })
        }
        clickListener.set(object : ItemClickListener {
            override fun onClick(article: ArticleModel) {
                itemClickData.value = article
            }
        })
        loadData.call()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("bind:response", "bind:clickListener")
        fun bindListAdapter(reyclerView: RecyclerView, response: TopHeadlineResponse?, clickListener: ItemClickListener) {
            if (response != null)
                reyclerView.adapter = NewsAdapter(response, clickListener)
        }
    }
}