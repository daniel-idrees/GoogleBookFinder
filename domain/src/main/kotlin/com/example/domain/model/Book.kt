package com.example.domain.model

data class Book(
    val title: String,
    val authors: List<String>,
    val imageUrl: String? = null,
)
