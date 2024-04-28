package com.example.ui.search.uievent

sealed interface SearchUiEvent {
    data class NavigateToResult(val searchQuery: String) : SearchUiEvent
    object EmptyTextError : SearchUiEvent
}