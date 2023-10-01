package com.example.ui.result.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BookDataResult
import com.example.domain.usecase.GetBookListUseCase
import com.example.ui.nav.ResultScreenArgumentSearchQueryKey
import com.example.ui.result.state.BookSearchResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _bookSearchResultState =
        MutableStateFlow<BookSearchResultState>(BookSearchResultState.Loading)
    val bookSearchResultState: StateFlow<BookSearchResultState>
        get() = _bookSearchResultState

    private val searchQuery = savedStateHandle.get<String>(ResultScreenArgumentSearchQueryKey)

    init {
        if (searchQuery != null) {
            searchBook(searchQuery)
        } else {
            _bookSearchResultState.value = BookSearchResultState.Error("Something went wrong")
        }
    }

    private fun searchBook(searchQuery: String) {
        viewModelScope.launch {
            _bookSearchResultState.emit(BookSearchResultState.Loading)
            val result = getBookListUseCase.get(searchQuery)
            updateUiState(result)
        }
    }

    private suspend fun updateUiState(result: BookDataResult) {
        val viewState = when (result) {
            is BookDataResult.Empty -> BookSearchResultState.EmptyResult
            is BookDataResult.Error -> BookSearchResultState.Error(result.errorMessage)
            is BookDataResult.Success -> BookSearchResultState.Success(result.data)
        }
        _bookSearchResultState.emit(viewState)
    }

    fun getAuthorText(authorsList: List<String>): String {
        if (authorsList.isEmpty()) {
            return "Unknown"
        }

        var authors = ""
        authorsList.forEach { a ->
            authors += if (authors.isNotEmpty()) ", $a" else a
        }

        return authors.ifEmpty { "Unknown" }
    }
}
