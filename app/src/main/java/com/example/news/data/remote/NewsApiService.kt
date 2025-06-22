package com.example.news.data.remote

import android.util.Log
import com.example.news.data.model.ApiNewsResponse
import com.example.news.BuildConfig
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.call.body

class ApiException(message: String?) : Exception(message)

interface NewsApiService {
    suspend fun getTopHeadlines(
        country: String = "us",
        page: Int,
        pageSize: Int
    ): ApiNewsResponse

    suspend fun searchNews(
        query: String,
        page: Int,
        pageSize: Int
    ): ApiNewsResponse
}

class NewsApiServiceImpl(private val client: HttpClient) : NewsApiService {

    private fun validateApiResponse(response: ApiNewsResponse): ApiNewsResponse {
        if (response.status != "ok") {
            Log.e("NewsApiService", "API Error: ${response.message}, Code: ${response.code}")
            throw ApiException(response.message)
        }
        if (response.articles.isNullOrEmpty()) {
            Log.w("NewsApiService", "No articles found for the given parameters.")
        } else {
            Log.d("NewsApiService", "API Response: $response")
        }
        return response
    }

    override suspend fun getTopHeadlines(
        country: String,
        page: Int,
        pageSize: Int
    ): ApiNewsResponse {
        try {
            val response: ApiNewsResponse = client.get {
                url("${BuildConfig.BASE_URL}v2/top-headlines")
                parameter("country", country)
                parameter("page", page)
                parameter("pageSize", pageSize)
                header("X-Api-Key", BuildConfig.API_KEY)
            }.body()

            return validateApiResponse(response)

        } catch (e: Exception) {
            Log.e("NewsApiService", "API Request failed: ${e.message}", e)
            throw e
        }
    }

    override suspend fun searchNews(
        query: String,
        page: Int,
        pageSize: Int
    ): ApiNewsResponse {
        try {
            val response: ApiNewsResponse = client.get {
                url("${BuildConfig.BASE_URL}v2/everything")
                parameter("q", query)
                parameter("page", page)
                parameter("pageSize", pageSize)
                header("X-Api-Key", BuildConfig.API_KEY)
            }.body()

            return validateApiResponse(response)

        } catch (e: Exception) {
            Log.e("NewsApiService", "API Request failed: ${e.message}", e)
            throw e
        }
    }
}
