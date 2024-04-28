package com.example.ui.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.di.DefaultDispatcher
import com.example.ui.search.action.SearchAction
import com.example.ui.search.uievent.SearchUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val actions: MutableSharedFlow<SearchAction> =
        MutableSharedFlow(extraBufferCapacity = 64)

    private val _events = Channel<SearchUiEvent>(capacity = 32)
    val events: Flow<SearchUiEvent> = _events.receiveAsFlow()

    init {
        actions
            .onEach(::handleAction)
            .flowOn(defaultDispatcher)
            .launchIn(viewModelScope)
    }

    fun onAction(action: SearchAction) = actions.tryEmit(action)

    private suspend fun handleAction(action: SearchAction) {
        when (action) {
            is SearchAction.SearchButtonClicked -> {
                if (action.searchQuery.isNotBlank()) {
                    _events.send(SearchUiEvent.NavigateToResult(action.searchQuery))
                } else {
                    _events.send(SearchUiEvent.EmptyTextError)
                }
            }
        }
    }
}
