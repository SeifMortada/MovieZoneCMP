package com.gameZone.usecase

import com.gameZone.models.Movie
import com.gameZone.repository.LocalDbRepository
import kotlinx.coroutines.flow.Flow

class RetrieveFavouriteMovies(private val localDbRepository: LocalDbRepository) {
    operator fun invoke(): Flow<List<Movie>> {
        return localDbRepository.getAllMovies()
    }
}