package com.gameZone.api.mapper

import com.gameZone.api.response.RemoteMovie
import com.gameZone.models.Movie

fun RemoteMovie.toDomainMovie(): Movie {
    val baseImageUrl = "https://image.tmdb.org/t/p/w500"
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = (baseImageUrl + posterPath) ?: "",
        releaseDate = releaseDate,
        genres = genreIds
    )
}