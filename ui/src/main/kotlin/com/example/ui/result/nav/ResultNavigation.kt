package com.example.ui.result.nav

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.ui.mainnav.ResultScreen
import java.net.URLDecoder

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
internal const val SEARCH_QUERY_ARG = "searchQuery"
const val RESULT_ROUTE = "result/{$SEARCH_QUERY_ARG}"

internal class ResultArgs(val searchQuery: String) {
    constructor(savedStateHandle: SavedStateHandle) :
        this(
            URLDecoder.decode(
                checkNotNull(savedStateHandle[SEARCH_QUERY_ARG]),
                URL_CHARACTER_ENCODING
            )
        )
}

fun NavGraphBuilder.resultScreen(
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = composable(ResultScreen.route, content = content)


fun NavController.navigateToResult(searchQuery: String) {
    this.navigate(
        ResultScreen.routeTo(searchQuery),
        navOptions {
            this.launchSingleTop = true
        },
    )
}

private fun ResultScreen.routeTo(searchQuery: String) =
    this.route.replace("{$SEARCH_QUERY_ARG}", searchQuery)
