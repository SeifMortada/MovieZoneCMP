package org.example.moviezone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gamezone.ui.theme.MovieZoneTheme
import favourites.navigation.FavouritesNav
import home.navigation.HomeNavigation
import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.moviezone.navigation.AppNavHost
import org.jetbrains.compose.ui.tooling.preview.Preview
import search.navigation.SearchNav

sealed class Item(val icon: ImageVector, val title: String, val route: Any) {
    data object Home : Item(Icons.Filled.Home, "Home", HomeNavigation)
    data object Search : Item(Icons.Filled.Search, "Search", SearchNav)
    data object Favourites : Item(Icons.Filled.Favorite, "Favourites", FavouritesNav)
}

val items = listOf(Item.Home, Item.Search, Item.Favourites)

private val logger = KotlinLogging.logger {}


@Composable
@Preview
fun App() {
    logger.debug { "Debugging: value = 123" }
    logger.info { "Some info here" }
    logger.error { "An error happened!" }
    val navHost = rememberNavController()
    val navBackStackEntry by navHost.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    MovieZoneTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                NavigationBar(
                    modifier = Modifier.fillMaxWidth().height(55.dp).background(
                        MaterialTheme.colorScheme.background
                    )
                ) {
                    items.forEach {
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = it.icon,
                                    contentDescription = it.title
                                )
                            },
                             label = { Text(it.title) },
                            selected = currentRoute == it.route,
                            onClick = {
                                if (currentRoute != it.route) {
                                    navHost.navigate(it.route) {
                                        popUpTo(navHost.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = Color.White,
                                selectedTextColor = MaterialTheme.colorScheme.onBackground,
                                unselectedTextColor = Color(0xFF9EB8A8),
                                indicatorColor = Color.Transparent
                            ),
                            modifier = Modifier.height(20.dp)
                        )
                    }
                }
            }) { innerPadding ->
            AppNavHost(navHost, innerPadding)
        }

    }
}