package com.example.data.repository

import com.example.data.model.BookDataResult
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(searchQuery: String): Flow<BookDataResult>
}
