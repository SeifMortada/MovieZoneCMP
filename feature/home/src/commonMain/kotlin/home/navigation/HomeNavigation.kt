package home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeNavigation

fun NavGraphBuilder.homeScreen(onMovieClick: (Int) -> Unit,paddingValues: PaddingValues){
    composable<HomeNavigation> {
        HomeRoute(onMovieClick = onMovieClick, paddingValues = paddingValues)
    }
}