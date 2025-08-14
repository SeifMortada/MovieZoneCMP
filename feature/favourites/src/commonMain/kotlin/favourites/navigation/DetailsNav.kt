package favourites.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import favourites.presentation.FavouritesRoute
import kotlinx.serialization.Serializable

@Serializable
data object FavouritesNav

fun NavGraphBuilder.favouritesScreen(onBackClick: () -> Unit) {
    composable<FavouritesNav> { backStackEntry ->
        FavouritesRoute { onBackClick }
    }
}