package com.example.ui.result.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.DefaultDispatcher
import com.example.data.model.BookDataResult
import com.example.domain.usecase.GetBookListUseCase
import com.example.ui.result.action.ResultAction
import com.example.ui.result.nav.SEARCH_QUERY_ARG
import com.example.ui.result.state.ResultViewState
import com.example.ui.result.uievent.ResultUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    savedStateHandle: SavedStateHandle,
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val actions: MutableSharedFlow<ResultAction> =
        MutableSharedFlow(extraBufferCapacity = 64)

    private val _events = Channel<ResultUiEvent>(capacity = 32)
    val events: Flow<ResultUiEvent> = _events.receiveAsFlow()

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
                        is BookDataResult.Success -> ResultViewState.Success(result.books)
                    }
                }.catch { emit(ResultViewState.Error) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = ResultViewState.Loading
        )

    init {
        actions
            .onEach(::handleAction)
            .flowOn(defaultDispatcher)
            .launchIn(viewModelScope)
    }

    fun onAction(action: ResultAction) = actions.tryEmit(action)

    private suspend fun handleAction(action: ResultAction) {
        when (action) {
            ResultAction.ResultItemClicked -> _events.send(ResultUiEvent.NavigateToDetails)
            ResultAction.ErrorAction -> _events.send(ResultUiEvent.ShowError)
            ResultAction.GoBackButtonClicked -> _events.send(ResultUiEvent.NavigateBack)
            ResultAction.NoInternetConnectionAction -> _events.send(ResultUiEvent.ShowNoInternetConnectionError)
            ResultAction.AfterErrorShownAction -> _events.send(ResultUiEvent.NavigateBack)
        }
    }
}
