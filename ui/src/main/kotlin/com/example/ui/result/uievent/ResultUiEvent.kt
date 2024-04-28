package com.example.ui.result.uievent

sealed interface ResultUiEvent {
    object NavigateToDetails : ResultUiEvent
    object ShowError : ResultUiEvent
    object ShowNoInternetConnectionError : ResultUiEvent
    object NavigateBack : ResultUiEvent
}
