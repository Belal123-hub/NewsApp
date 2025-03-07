package com.example.news.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiNewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ApiArticle>
)