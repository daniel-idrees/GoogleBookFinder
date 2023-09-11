package com.example.googlebookfinder.domain.repository

import com.example.googlebookfinder.domain.model.Book

interface BookRepository {
    suspend fun getBooks(searchQuery: String): List<Book>?
}
