package com.example.ui.result.uievent

sealed interface ResultUiEvent {
    data object NavigateToDetails : ResultUiEvent
    data object ShowError : ResultUiEvent
    data object ShowNoInternetConnectionError : ResultUiEvent
    data object NavigateBack : ResultUiEvent
}
