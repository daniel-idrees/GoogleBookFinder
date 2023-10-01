package com.example.data.dto

data class SearchBookResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<Item>,
)
