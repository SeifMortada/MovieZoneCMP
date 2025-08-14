package com.gameZone.api.mapper

import com.gameZone.api.response.Genre
import com.gameZone.api.response.ProductionCompany
import com.gameZone.api.response.RemoteMovie
import com.gameZone.models.Movie
import com.gameZone.models.MovieDetails

const val baseImageUrl = "https://image.tmdb.org/t/p/w500"

fun RemoteMovie.toDomainMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = (baseImageUrl + posterPath) ?: "",
        releaseDate = releaseDate,
        genres = genreIds ?: listOf()
    )
}

fun RemoteMovie.toDomainMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        genres = genres?.mapNotNull { it.toDomainGenre() } ?: listOf(),
        imageUrl = (baseImageUrl + posterPath),
        voteAverage = voteAverage,
        voteCount = voteCount,
        budget = budget ?: 0,
        revenue = revenue ?: 0,
        spokenLanguage = spokenLanguages?.mapNotNull { it.englishName } ?: listOf(),
        productionCompanies = productionCompanies?.mapNotNull { it.toDomain() } ?: listOf(),
        status = status ?: "",
        homepage = homepage ?: ""
    )
}

fun MovieDetails.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = imageUrl,
        releaseDate = releaseDate,
        genres = genres.mapNotNull { it.id }
    )
}

fun Genre.toDomainGenre(): com.gameZone.models.Genre {
    return com.gameZone.models.Genre(
        id = id,
        name = name
    )
}

fun ProductionCompany.toDomain(): com.gameZone.models.ProductionCompany {
    return com.gameZone.models.ProductionCompany(
        id = id,
        name = name,
        logoPath = if (logoPath.isNullOrEmpty()) "" else baseImageUrl + logoPath,
        originCountry = originCountry
    )
}