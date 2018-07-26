package com.example.rohitsingh.news.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.rohitsingh.news.R
import com.example.rohitsingh.news.databinding.ActivityMainBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory : MainActivityViewModelFactory
    private lateinit var binding : ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initDataBinding()
        subscribeToNewsItemClick()
    }

    private fun initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setLifecycleOwner(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MainActivityViewModel::class.java]
        binding.viewModel = viewModel
    }

    private fun subscribeToNewsItemClick() {
        viewModel.itemClickData.observe(this, Observer {
            val intent  = Intent().apply {
                data = Uri.parse(it?.url)
                action = Intent.ACTION_VIEW
            }
            startActivity(intent)
        })
    }
}
