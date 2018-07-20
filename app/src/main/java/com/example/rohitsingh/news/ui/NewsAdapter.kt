package com.example.rohitsingh.news.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.example.rohitsingh.news.R
import android.view.ViewGroup
import android.widget.TextView
import com.example.rohitsingh.news.repository.ArticleModel
import com.example.rohitsingh.news.repository.TopHeadlineResponse

class NewsAdapter(val topHeadlineResponse: TopHeadlineResponse, val clickListener: ItemClickListener) : RecyclerView.Adapter<NewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return NewsViewHolder(view)
    }


    override fun getItemCount(): Int = topHeadlineResponse.articles.size


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var article = topHeadlineResponse.articles.get(position)
        holder.updateView(article)
        holder.itemView.setOnClickListener {
            clickListener.onClick(article = article)
        }
    }
}

class NewsViewHolder(itemVIew: View) : RecyclerView.ViewHolder(itemVIew) {

    var titleTv = itemVIew.findViewById<TextView>(R.id.title_tv)
    var descTv = itemVIew.findViewById<TextView>(R.id.desc_tv)

    fun updateView(article: ArticleModel) {
        titleTv.text = article.title
        descTv.text = article.description
    }

}

interface ItemClickListener {
    fun onClick(article: ArticleModel)
}