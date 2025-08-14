package details.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import details.presentation.MovieDetailsRoute
import kotlinx.serialization.Serializable

@Serializable
data class DetailsNav(val id: Int)

fun NavGraphBuilder.detailsScreen(onBackClick: () -> Unit,paddingValues: PaddingValues){
    composable<DetailsNav> { backStackEntry ->
        val args = backStackEntry.toRoute<DetailsNav>()
        MovieDetailsRoute(movieId = args.id, onBackClick = onBackClick,paddingValues = paddingValues)
    }
}