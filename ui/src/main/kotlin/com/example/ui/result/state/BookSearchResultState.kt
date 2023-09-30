package com.example.ui.result.state

import com.example.domain.model.Book

sealed class BookSearchResultState {
    data class Success(val books: List<Book>) : BookSearchResultState()
    data class Error(val errorMessage: String) : BookSearchResultState()
    object Loading : BookSearchResultState()
    object EmptyResult : BookSearchResultState()
}
