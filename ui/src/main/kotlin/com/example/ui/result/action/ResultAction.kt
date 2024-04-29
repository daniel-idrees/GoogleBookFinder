package com.example.ui.result.action

sealed interface ResultAction {
    data object ResultItemClicked : ResultAction
    data object NoInternetConnectionAction: ResultAction
    data object ErrorAction: ResultAction
    data object GoBackButtonClicked: ResultAction
    data object AfterErrorShownAction: ResultAction
}
