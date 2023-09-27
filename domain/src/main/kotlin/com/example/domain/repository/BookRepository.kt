package com.example.domain.repository

import com.example.domain.model.Book

interface BookRepository {
    suspend fun getBooks(searchQuery: String): List<Book>?
}
