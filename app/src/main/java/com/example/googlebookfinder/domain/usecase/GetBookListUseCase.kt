package com.example.googlebookfinder.domain.usecase

import com.example.googlebookfinder.domain.model.Book
import com.example.googlebookfinder.domain.repository.BookRepository
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val bookRepository: BookRepository,
) {
    suspend fun get(searchQuery: String): List<Book>? {
        return bookRepository.getBooks(searchQuery)
    }
}
