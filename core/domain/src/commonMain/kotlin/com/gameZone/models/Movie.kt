package com.gameZone.models

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val genres: List<Int>,
    var isFavorite: Boolean = false
)
