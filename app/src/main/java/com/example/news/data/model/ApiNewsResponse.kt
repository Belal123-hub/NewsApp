package com.example.news.data.model

import com.example.news.data.local.Article
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiNewsResponse(
    @SerialName("status")
    val status: String,
    @SerialName("totalResults")
    val totalResults: Int? = null,
    @SerialName("articles")
    val articles: List<ApiArticle>? = emptyList(),
    @SerialName("message")
    val message: String? = null,
    @SerialName("code")
    val code: String? = null
)