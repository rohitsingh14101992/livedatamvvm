package com.example.rohitsingh.news

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.example.rohitsingh.news.network.*

fun <T, X> switchMapForApiResponse(liveData: LiveData<ApiResponse<T>>, doOnSubscribe: (() -> Unit)? = null, doOnSuccess: (((T?) -> X?)?) = null,
                                   doOnError: (((Throwable) -> Unit)?) = null): LiveData<X?>? {

    val response = Transformations.map(liveData) {
        when (it) {
            is ApiIsLoading -> {
                doOnSubscribe?.invoke()
                null
            }
            is ApiSuccessResponse -> {
                val responseBody = it.body
                doOnSuccess?.invoke(responseBody)
            }
            is ApiEmptyResponse<*> -> {
                doOnSuccess?.invoke(null)
                null
            }
            is ApiErrorResponse<*> -> {
                doOnError?.invoke(it.errorMessage)
                null
            }
            else -> null
        }
    }
    return response
}