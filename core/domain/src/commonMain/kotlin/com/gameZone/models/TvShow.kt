package com.gameZone.models

data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    val originalLanguage: String,
    val firstAirDate: String,
    val genreIds: List<Int>
)
