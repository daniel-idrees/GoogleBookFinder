package com.example.ui.result.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BookDataResult
import com.example.domain.usecase.GetBookListUseCase
import com.example.ui.result.nav.SEARCH_QUERY_ARG
import com.example.ui.result.state.ResultViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val searchQuery = savedStateHandle.getStateFlow(
        key = SEARCH_QUERY_ARG,
        initialValue = ""
    )

    val resultViewState: StateFlow<ResultViewState> =
        searchQuery.flatMapLatest { query ->
            getBookListUseCase(query)
                .map { result ->
                    when (result) {
                        BookDataResult.Empty -> ResultViewState.Empty
                        BookDataResult.Error -> ResultViewState.Error
                        BookDataResult.NoInternetConnection -> ResultViewState.NoInternetConnection
                        is BookDataResult.Success -> ResultViewState.Success(result.data)
                    }
                }.catch { emit(ResultViewState.Error) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = ResultViewState.Loading
        )
}
