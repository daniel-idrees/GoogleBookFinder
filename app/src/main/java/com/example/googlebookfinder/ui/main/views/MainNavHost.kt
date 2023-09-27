package com.example.googlebookfinder.ui.main.views

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.googlebookfinder.ui.main.MainViewModel
import com.example.googlebookfinder.ui.theme.GoogleBookFinderTheme

@Composable
fun MainNavHost(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val viewState by viewModel.bookSearchResultState.collectAsStateWithLifecycle()

    GoogleBookFinderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            NavHost(navController = navController, startDestination = SearchScreen.route) {
                toResultScreen {
                    ResultScreenView(
                        viewState,
                        viewModel::resetState,
                    ) { navController.popBackStack(SearchScreen.route, false) }
                }

                toSearchScreen {
                    SearchScreenView(
                        viewState,
                        { query -> viewModel.searchBook(query) },
                    ) { navController.navigate(ResultScreen.route) }
                }
            }
        }
    }
}

private fun NavGraphBuilder.toResultScreen(
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = composable(ResultScreen.route, content = content)

private fun NavGraphBuilder.toSearchScreen(
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) = composable(SearchScreen.route, content = content)
