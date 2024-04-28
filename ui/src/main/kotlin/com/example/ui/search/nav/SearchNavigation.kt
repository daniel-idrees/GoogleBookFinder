package com.example.ui.search.nav

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.ui.mainnav.SearchScreen

const val SEARCH_ROUTE = "search"

fun NavGraphBuilder.searchScreen(
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = composable(SearchScreen.route, content = content)

fun NavController.navigateToSearch() {
    this.navigate(
        SearchScreen.route,
        navOptions {
            this.launchSingleTop = true
        },
    )
}
