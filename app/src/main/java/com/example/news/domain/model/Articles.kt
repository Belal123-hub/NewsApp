package com.example.news.domain.model


data class Article(
    val id: String,
    val title: String,
    val description: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String,
    val source: String,
){
    fun getShareText(): String {
        return "$title\n\n$url"
    }
}