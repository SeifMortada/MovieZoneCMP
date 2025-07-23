package org.example.moviezone

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import org.example.moviezone.navigation.AppNavHost
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        AppNavHost()
    }
}