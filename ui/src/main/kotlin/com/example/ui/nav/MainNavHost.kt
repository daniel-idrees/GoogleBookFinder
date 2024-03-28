package com.example.ui.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ui.nav.SearchScreen
import com.example.ui.result.ResultScreenView
import com.example.ui.result.navigation.navigateToResult
import com.example.ui.result.navigation.resultScreen
import com.example.ui.search.SearchScreenView
import com.example.ui.search.nav.searchScreen

@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SearchScreen.route,
    ) {
        resultScreen {
            ResultScreenView(
                viewModel = hiltViewModel(),
            ) { navController.popBackStack(SearchScreen.route, false) }
        }

        searchScreen {
            SearchScreenView(
                navigateToResult = navController::navigateToResult,
            )
        }
    }
}
