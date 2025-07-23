package com.gameZone.repository

import com.gameZone.models.ApiOperation
import com.gameZone.models.Movie

interface MoviesRepository {
    suspend fun getPopularMovies(): ApiOperation<List<Movie>>
}