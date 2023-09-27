package com.example.domain.usecase

import com.example.domain.model.Book
import com.example.domain.repository.BookRepository
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val bookRepository: BookRepository,
) {
    suspend fun get(searchQuery: String): List<Book>? {
        return bookRepository.getBooks(searchQuery)
    }
}
