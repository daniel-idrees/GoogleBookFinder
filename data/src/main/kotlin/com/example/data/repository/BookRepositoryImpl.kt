package com.example.data.repository

import com.example.core.util.runSuspendCatching
import com.example.data.dto.mapper.toBookList
import com.example.data.network.BookFinderService
import com.example.domain.model.BookDataResult
import com.example.domain.repository.BookRepository
import java.io.IOException
import javax.inject.Inject

internal class BookRepositoryImpl @Inject constructor(
    private val bookFinderService: BookFinderService,
) : BookRepository {
    override suspend fun getBooks(searchQuery: String): BookDataResult =
        runSuspendCatching {
            val response = bookFinderService.getBookList(searchQuery).toBookList()
            if (response.isNotEmpty()) {
                BookDataResult.Success(response)
            } else {
                BookDataResult.Empty
            }
        }.onFailure {
            // log
        }.getOrElse {
            getErrorResult(it)
        }

    private fun getErrorResult(throwable: Throwable): BookDataResult {
        return when (throwable) {
            is IOException -> BookDataResult.NoInternetConnection
            else -> BookDataResult.Error
        }
    }
}
