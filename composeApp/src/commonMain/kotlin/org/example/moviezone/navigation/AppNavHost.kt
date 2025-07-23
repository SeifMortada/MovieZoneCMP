package org.example.moviezone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import home.HomeRoute

@Composable
fun AppNavHost() {
    val navHostController = rememberNavController()
    NavHost(navHostController, startDestination = Dest.Home) {
        composable<Dest.Home> {
            HomeRoute()
        }

    }
}