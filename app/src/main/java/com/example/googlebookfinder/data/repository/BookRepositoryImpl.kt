package com.example.googlebookfinder.data.repository

import com.example.googlebookfinder.data.dto.map
import com.example.googlebookfinder.data.network.BookFinderService
import com.example.googlebookfinder.domain.model.Book
import com.example.googlebookfinder.domain.repository.BookRepository
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookFinderService: BookFinderService,
) : BookRepository {
    override suspend fun getBooks(searchQuery: String): List<Book>? =
        runCatching {
            bookFinderService.getBookList(searchQuery).map()
        }.onFailure {
            // TODO log
        }.getOrNull()
}
