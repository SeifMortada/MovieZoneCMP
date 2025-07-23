package com.gameZone.api.mapper

import com.gameZone.api.response.RemoteMovie
import com.gameZone.models.Movie

fun RemoteMovie.toDomainMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate,
        genres = genreIds
    )
}