package com.example.domain.repository

import com.example.domain.model.BookDataResult

interface BookRepository {
    suspend fun getBooks(searchQuery: String): BookDataResult
}
