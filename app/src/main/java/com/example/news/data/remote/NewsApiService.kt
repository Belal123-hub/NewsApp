package com.example.news.data.remote

import android.util.Log
import com.example.news.data.model.ApiNewsResponse
import com.example.news.BuildConfig
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.call.body

interface NewsApiService {
    suspend fun getTopHeadlines(
        country: String = "us",
        page: Int,
        pageSize: Int
    ): ApiNewsResponse
}

class NewsApiServiceImpl(private val client: HttpClient) : NewsApiService {
    override suspend fun getTopHeadlines(
        country: String,
        page: Int,
        pageSize: Int
    ): ApiNewsResponse {
        return try {
            val response: ApiNewsResponse = client.get {
                url("${BuildConfig.BASE_URL}v2/top-headlines")
                parameter("country", country)
                parameter("page", page)
                parameter("pageSize", pageSize)
                header("X-Api-Key", BuildConfig.API_KEY)
            }.body()

            Log.d("NewsApiService", "API Response: $response") // Log response
            response
        } catch (e: Exception) {
            Log.e("NewsApiService", "API Request failed: ${e.message}", e)
            throw e
        }
    }
}