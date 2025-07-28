package search.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import search.presentation.SearchRoute
import kotlinx.serialization.Serializable

@Serializable
data object SearchNav

fun NavGraphBuilder.searchScreen(onBackClick:()->Unit){
    composable<SearchNav> {
        SearchRoute(onBackClick =onBackClick )
    }
}