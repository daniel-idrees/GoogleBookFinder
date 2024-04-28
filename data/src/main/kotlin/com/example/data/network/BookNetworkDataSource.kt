package com.example.data.network

import com.example.data.network.model.SearchResult

internal interface BookNetworkDataSource {
    suspend fun searchBooks(searchQuery: String): SearchResult
}