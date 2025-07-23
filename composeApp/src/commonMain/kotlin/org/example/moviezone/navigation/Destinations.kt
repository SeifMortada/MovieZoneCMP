package org.example.moviezone.navigation


import kotlinx.serialization.Serializable

sealed interface Dest {
    @Serializable
    data object Home: Dest

}