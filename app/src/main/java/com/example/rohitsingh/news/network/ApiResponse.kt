package com.example.rohitsingh.news.network

import retrofit2.Response
import java.util.regex.Pattern

@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<T> {
    companion object {

        fun <T> createLoadingResponse() : ApiIsLoading<T> = ApiIsLoading()

        fun <T> create(error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(error)
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                            body = body,
                            linkHeader = response.headers()?.get("link")
                    )
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    response.message()
                } else {
                    msg
                }
                ApiErrorResponse(Exception(errorMsg))
            }
        }
    }
}

/**
 * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()


class ApiIsLoading<T> : ApiResponse<T> ()


data class ApiSuccessResponse<T>(
        val body: T,
        val links: Map<String, String?>
) : ApiResponse<T>() {
    constructor(body: T) : this(body, null)
    constructor(body: T, linkHeader: String?) : this(
            body = body,
            links = linkHeader?.extractLinks() ?: emptyMap()
    )


    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")
        private val PAGE_PATTERN = Pattern.compile("\\bpage=(\\d+)")
        private const val NEXT_LINK = "next"

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
            return links
        }

    }
}

data class ApiErrorResponse<T>(val errorMessage: Throwable) : ApiResponse<T>()

