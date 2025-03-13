package com.example.news.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiSource(
    val id: String?=null,
    val name: String
)