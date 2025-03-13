package com.example.news.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey val url: String, // Mark url as the primary key
    val title: String,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String,
    val source: String
)