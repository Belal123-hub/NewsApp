package com.example.news.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiArticle(
    val source: ApiSource,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?
)
