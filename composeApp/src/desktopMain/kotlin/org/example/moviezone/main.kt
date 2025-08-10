package org.example.moviezone

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.moviezone.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "movie zone",
    ) {
        initKoin()
        App()
    }
}