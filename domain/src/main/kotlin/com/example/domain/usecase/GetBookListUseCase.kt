package com.example.domain.usecase

import com.example.data.model.BookDataResult
import com.example.data.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val bookRepository: BookRepository,
) {
    suspend operator fun invoke(
        searchQuery: String,
    ): Flow<BookDataResult> = bookRepository.searchBooks(searchQuery)
}
