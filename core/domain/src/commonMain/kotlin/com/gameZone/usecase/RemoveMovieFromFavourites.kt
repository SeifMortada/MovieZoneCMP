package com.gameZone.usecase

import com.gameZone.repository.LocalDbRepository

class RemoveMovieFromFavourites(private val localDbRepository: LocalDbRepository) {
    suspend operator fun invoke(movieId: Int) = localDbRepository.deleteMovieById(movieId)
}