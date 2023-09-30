package com.example.domain.model

import java.io.IOException

sealed class BookDataResult {
    data class Success(val data: List<Book>) : BookDataResult()
    data class Error(val errorMessage: String) : BookDataResult()
    object Empty : BookDataResult()
}

fun getErrorResult(throwable: Throwable): BookDataResult.Error {
    return when (throwable) {
        is IOException -> BookDataResult.Error("Please check your connection")
        else -> BookDataResult.Error("Something went wrong")
    }
}
