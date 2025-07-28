package com.gameZone.api.mapper

import com.gameZone.api.response.RemoteTvShow
import com.gameZone.models.TvShow

fun RemoteTvShow.toDomainTvShow(): TvShow {
    val baseImageUrl = "https://image.tmdb.org/t/p/w500"
    return TvShow(
        id = this.id,
        name = this.name,
        posterPath = (baseImageUrl + posterPath),
        overview = this.overview,
        firstAirDate = this.firstAirDate ?: "",
        originalLanguage = this.originalLanguage,
        genreIds = this.genreIds
    )
}