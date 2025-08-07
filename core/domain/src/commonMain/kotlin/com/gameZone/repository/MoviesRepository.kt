package com.gameZone.repository

import com.gameZone.models.ApiOperation
import com.gameZone.models.Movie
import com.gameZone.models.MovieDetails

interface MoviesRepository {
    suspend fun getPopularMovies(): ApiOperation<List<Movie>>

    suspend fun getTopRatedMovies(): ApiOperation<List<Movie>>

    suspend fun getMovieDetails(movieId: Int): ApiOperation<MovieDetails>

    suspend fun searchMovies(query: String): ApiOperation<List<Movie>>

}