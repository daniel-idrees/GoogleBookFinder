package com.example.data.model

sealed interface BookDataResult {
    data class Success(val books: List<Book>) : BookDataResult
    data object Error : BookDataResult
    data object Empty : BookDataResult
    data object NoInternetConnection : BookDataResult
}
