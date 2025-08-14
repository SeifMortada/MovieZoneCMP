package com.gameZone.usecase

import com.gameZone.models.Movie
import com.gameZone.repository.LocalDbRepository

class AddMovieToFavorites(private val localDbRepository: LocalDbRepository) {
    suspend operator fun invoke(movie: Movie) {
        localDbRepository.insertMovie(movie)
    }
}