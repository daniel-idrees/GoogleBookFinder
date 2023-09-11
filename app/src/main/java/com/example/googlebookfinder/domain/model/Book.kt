package com.example.googlebookfinder.domain.model

data class Book(
    val title: String? = null,
    val authors: List<String>? = null,
    val imageUrl: String? = null,
)
