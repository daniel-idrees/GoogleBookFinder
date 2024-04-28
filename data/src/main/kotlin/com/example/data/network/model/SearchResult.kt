package com.example.data.network.model

internal data class SearchResult(
    val kind: String,
    val totalItems: Int,
    val items: List<Item>,
)
