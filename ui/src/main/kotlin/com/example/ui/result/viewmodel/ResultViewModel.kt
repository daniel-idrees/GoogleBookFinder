package com.example.ui.result.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.IoDispatcher
import com.example.domain.model.Book
import com.example.domain.usecase.GetBookListUseCase
import com.example.ui.nav.ResultScreenArgumentSearchQueryKey
import com.example.ui.result.state.BookSearchResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _bookSearchResultState =
        MutableStateFlow<BookSearchResultState>(BookSearchResultState.EmptyResult)
    val bookSearchResultState: StateFlow<BookSearchResultState>
        get() = _bookSearchResultState

    private val searchQuery = savedStateHandle.get<String>(ResultScreenArgumentSearchQueryKey)

    init {
        if (searchQuery != null) {
            searchBook(searchQuery)
        } else {
            _bookSearchResultState.value = BookSearchResultState.Error
        }
    }

    private fun searchBook(searchQuery: String) {
        viewModelScope.launch(ioDispatcher) {
            _bookSearchResultState.emit(BookSearchResultState.Loading)
            val list = getBookListUseCase.get(searchQuery)
            updateUiState(list)
        }
    }

    private suspend fun updateUiState(list: List<Book>?) {
        when {
            list == null -> _bookSearchResultState.emit(BookSearchResultState.Error)
            list.isNotEmpty() -> _bookSearchResultState.emit(BookSearchResultState.Success(list))
            else -> _bookSearchResultState.emit(BookSearchResultState.EmptyResult)
        }
    }
}
