import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import favourites.navigation.FavouritesNav
import home.navigation.HomeNavigation
import search.navigation.SearchNav

sealed class BottomNavDestination(
    val route: Any,
    val label: String,
    val icon: ImageVector
) {
    data object Home : BottomNavDestination(HomeNavigation, "Home", Icons.Default.Home)
    data object Search : BottomNavDestination(SearchNav, "Search", Icons.Default.Search)
    data object Favourites : BottomNavDestination(FavouritesNav, "Favourites", Icons.Default.Favorite)
}

val bottomNavItems = listOf(
    BottomNavDestination.Home,
    BottomNavDestination.Search,
    BottomNavDestination.Favourites
)

