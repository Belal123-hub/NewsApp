package com.example.news.domain.repository

import com.example.news.domain.model.Article

interface NewsRepository {
    suspend fun getTopHeadLines(
        country: String,
        page: Int,
        pageSize: Int,
     ):List<Article>
}