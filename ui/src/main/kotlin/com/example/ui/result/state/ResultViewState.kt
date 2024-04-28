package com.example.ui.result.state

import com.example.data.model.Book

sealed interface ResultViewState {
    data class Success(val books: List<com.example.data.model.Book>) : ResultViewState
    object Error : ResultViewState
    object Loading : ResultViewState
    object Empty : ResultViewState
    object NoInternetConnection: ResultViewState
}
