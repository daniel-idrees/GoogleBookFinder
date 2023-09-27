package com.example.data.repository

import com.example.data.dto.map
import com.example.data.network.BookFinderService
import com.example.domain.model.Book
import com.example.domain.repository.BookRepository
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
