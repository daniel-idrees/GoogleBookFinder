package com.example.ui.result.action

sealed interface ResultAction {
    object ResultItemClicked : ResultAction
    object NoInternetConnectionAction: ResultAction
    object ErrorAction: ResultAction
    object GoBackButtonClicked: ResultAction
    object AfterErrorShownAction: ResultAction
}
