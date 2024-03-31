package com.example.domain.usecase

import com.example.domain.model.BookDataResult
import com.example.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val bookRepository: BookRepository,
) {
    suspend operator fun invoke(
        searchQuery: String,
    ): Flow<BookDataResult> = flowOf(bookRepository.getBooks(searchQuery))
}
