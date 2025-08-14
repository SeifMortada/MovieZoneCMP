package org.example.moviezone.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import details.navigation.DetailsNav
import details.navigation.detailsScreen
import favourites.navigation.favouritesScreen
import home.navigation.HomeNavigation
import home.navigation.homeScreen
import search.navigation.searchScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController(),
    paddingValues: PaddingValues = PaddingValues()
) {
    NavHost(navHostController, startDestination = HomeNavigation) {
        homeScreen(
            paddingValues = paddingValues,
            onMovieClick = { movieId ->
                navHostController.navigate(DetailsNav(movieId))
            }
        )
        detailsScreen (
            onBackClick = { navHostController.popBackStack() },
            paddingValues = paddingValues
        )
        searchScreen(
            onBackClick = { navHostController.popBackStack() },
            onMovieClick = { movieId ->
                navHostController.navigate(DetailsNav(movieId))
            }
        )
        favouritesScreen {
            navHostController.popBackStack()
        }
    }
}