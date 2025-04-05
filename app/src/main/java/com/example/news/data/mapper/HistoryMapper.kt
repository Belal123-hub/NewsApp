package com.example.news.data.mapper

import com.example.news.data.local.HistoryArticleEntity
import com.example.news.domain.model.HistoryArticles

fun HistoryArticles.toEntity() = HistoryArticleEntity(
    url = url,
    title = title,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    source = source,
    accessedAt = accessedAt
)

fun HistoryArticleEntity.toDomain() = HistoryArticles(
    url = url,
    title = title,
    description = description,
    urlToImage = urlToImage,
    publishedAt = publishedAt,
    source = source,
    accessedAt = accessedAt
)