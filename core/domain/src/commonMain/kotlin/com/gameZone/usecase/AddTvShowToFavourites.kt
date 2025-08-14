package com.gameZone.usecase

import com.gameZone.models.TvShow
import com.gameZone.repository.LocalDbRepository

class AddTvShowToFavourites(private val localDbRepository: LocalDbRepository) {

    suspend operator fun invoke(tvShow: TvShow) {
        localDbRepository.insertTvShow(tvShow)
    }
}