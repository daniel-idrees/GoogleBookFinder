package com.example.data.model

sealed interface BookDataResult {
    data class Success(val books: List<Book>) : BookDataResult
    object Error : BookDataResult
    object Empty : BookDataResult
    object NoInternetConnection : BookDataResult
}
