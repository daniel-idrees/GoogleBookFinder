package com.example.ui.search.action

sealed interface SearchAction {
    data class SearchButtonClicked(val searchQuery: String): SearchAction
}
