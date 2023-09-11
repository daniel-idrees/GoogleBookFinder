package com.example.googlebookfinder.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlebookfinder.domain.di.IoDispatcher
import com.example.googlebookfinder.domain.usecase.GetBookListUseCase
import com.example.googlebookfinder.ui.main.state.BookSearchResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _bookSearchResultState =
        MutableStateFlow<BookSearchResultState>(BookSearchResultState.Empty)
    val bookSearchResultState: StateFlow<BookSearchResultState>
        get() = _bookSearchResultState

    fun searchBook(searchQuery: String) {
        viewModelScope.launch(ioDispatcher) {
            _bookSearchResultState.emit(BookSearchResultState.Loading)
            val list = getBookListUseCase.get(searchQuery)
            if (list != null) {
                _bookSearchResultState.emit(BookSearchResultState.Success(list))
            } else {
                _bookSearchResultState.emit(BookSearchResultState.Error)
            }
        }
    }

    fun resetState() {
        _bookSearchResultState.value = BookSearchResultState.Empty
    }
}
