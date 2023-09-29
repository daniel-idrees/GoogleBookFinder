package com.example.ui.result.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.ui.nav.ResultScreen
import com.example.ui.nav.ResultScreenArgumentSearchQueryKey

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
    this.route.replace("{$ResultScreenArgumentSearchQueryKey}", searchQuery)
