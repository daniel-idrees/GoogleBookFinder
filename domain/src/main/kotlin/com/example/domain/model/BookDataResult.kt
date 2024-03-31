package com.example.domain.model

sealed interface BookDataResult {
    data class Success(val data: List<Book>) : BookDataResult
    object Error : BookDataResult
    object Empty : BookDataResult

    object NoInternetConnection : BookDataResult
}
