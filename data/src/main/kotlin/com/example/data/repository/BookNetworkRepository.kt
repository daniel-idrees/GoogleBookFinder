package com.example.data.repository

import com.example.core.util.runSuspendCatching
import com.example.data.model.BookDataResult
import com.example.data.model.mapper.toBookList
import com.example.data.network.BookNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.io.IOException
import javax.inject.Inject

internal class BookNetworkRepository @Inject constructor(
    private val network: BookNetworkDataSource,
) : BookRepository {
    override suspend fun searchBooks(searchQuery: String): Flow<BookDataResult> =
        runSuspendCatching {
            val response = network.searchBooks(searchQuery).toBookList()
            if (response.isNotEmpty()) {
                flowOf(BookDataResult.Success(response))
            } else {
                flowOf(BookDataResult.Empty)
            }
        }.onFailure {
            // log
        }.getOrElse {
            flowOf(getErrorResult(it))
        }

    private fun getErrorResult(throwable: Throwable): BookDataResult {
        return when (throwable) {
            is IOException -> BookDataResult.NoInternetConnection
            else -> BookDataResult.Error
        }
    }
}
