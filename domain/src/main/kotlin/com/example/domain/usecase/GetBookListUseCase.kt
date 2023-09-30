package com.example.domain.usecase

import com.example.domain.model.BookDataResult
import com.example.domain.repository.BookRepository
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val bookRepository: BookRepository,
) {
    suspend fun get(
        searchQuery: String,
    ): BookDataResult = bookRepository.getBooks(searchQuery)
}
