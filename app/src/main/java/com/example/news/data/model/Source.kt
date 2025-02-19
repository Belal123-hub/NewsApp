package com.example.news.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String?,
    val name: String
)