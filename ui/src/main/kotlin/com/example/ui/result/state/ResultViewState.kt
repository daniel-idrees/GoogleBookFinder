package com.example.ui.result.state

import com.example.data.model.Book

sealed interface ResultViewState {
    data class Success(val books: List<Book>) : ResultViewState
    data object Error : ResultViewState
    data object Loading : ResultViewState
    data object Empty : ResultViewState
    data object NoInternetConnection: ResultViewState
}
