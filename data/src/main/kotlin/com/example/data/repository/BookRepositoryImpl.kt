package com.example.data.repository

import com.example.core.di.IoDispatcher
import com.example.data.dto.mapper.toBookList
import com.example.data.network.BookFinderService
import com.example.domain.model.BookDataResult
import com.example.domain.model.getErrorResult
import com.example.domain.repository.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookFinderService: BookFinderService,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : BookRepository {
    override suspend fun getBooks(searchQuery: String): BookDataResult =
        withContext(ioDispatcher) {
            runCatching {
                val response = bookFinderService.getBookList(searchQuery).toBookList()
                if (response.isNotEmpty()) {
                    BookDataResult.Success(response)
                } else {
                    BookDataResult.Empty
                }
            }.getOrElse {
                getErrorResult(it)
            }
        }
}
