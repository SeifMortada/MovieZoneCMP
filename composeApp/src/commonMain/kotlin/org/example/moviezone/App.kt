package org.example.moviezone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import details.navigation.DetailsNav
import home.navigation.HomeNavigation
import moviezone.composeapp.generated.resources.Res
import moviezone.composeapp.generated.resources.ic_add
import moviezone.composeapp.generated.resources.ic_home
import moviezone.composeapp.generated.resources.ic_search
import org.example.moviezone.navigation.AppNavHost
import org.example.moviezone.theme.MovieZoneTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import search.navigation.SearchNav

sealed class Item(val icon: DrawableResource, val title: String, val route: Any) {
    data object Home : Item(Res.drawable.ic_home, "Home", HomeNavigation)
    data object Search : Item(Res.drawable.ic_search, "Search",SearchNav)
    data object Details : Item(Res.drawable.ic_add, "Details", DetailsNav)
}

val items = listOf(Item.Home, Item.Search, Item.Details)

@Composable
@Preview
fun App() {
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
                                    painter = painterResource(it.icon),
                                    contentDescription = it.title
                                )
                            },
                            //  label = { Text(it.title) },
                            selected = true,
                            onClick =  {
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
                                selectedIconColor = MaterialTheme.colorScheme.onBackground,
                                unselectedIconColor = Color(0xFF9EB8A8),
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