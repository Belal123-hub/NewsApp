package com.example.news.domain.model

data class HistoryArticles(
    val url: String,
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val source: String,
    val accessedAt: Long = System.currentTimeMillis()
)