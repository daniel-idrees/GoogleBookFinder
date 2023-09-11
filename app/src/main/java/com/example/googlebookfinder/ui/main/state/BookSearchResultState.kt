package com.example.googlebookfinder.ui.main.state

import com.example.googlebookfinder.domain.model.Book

sealed class BookSearchResultState {
    data class Success(val books: List<Book>) : BookSearchResultState()
    object Error : BookSearchResultState()
    object Loading : BookSearchResultState()
    object Empty : BookSearchResultState()
}
