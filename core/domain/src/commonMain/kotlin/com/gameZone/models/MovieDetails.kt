package com.gameZone.models

data class MovieDetails(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val homepage: String,
    val budget: Int,
    val revenue: Int,
    val genres: List<Genre>,
    val status: String,
    val spokenLanguage: List<String>,
    val productionCompanies: List<ProductionCompany>
)
