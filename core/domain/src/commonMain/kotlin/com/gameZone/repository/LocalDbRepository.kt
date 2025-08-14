package com.gameZone.repository

import com.gameZone.models.Movie
import com.gameZone.models.TvShow
import kotlinx.coroutines.flow.Flow

interface LocalDbRepository {

    // Movies
    suspend fun insertMovie(movie: Movie)
    suspend fun deleteMovieById(id: Int)
    fun getAllMovies(): Flow<List<Movie>>
    suspend fun getMovieById(id: Int): Movie?

    // TV Shows
    suspend fun insertTvShow(tvShow: TvShow)
    suspend fun deleteTvShowById(id: Int)
    fun getAllTvShows(): Flow<List<TvShow>>
    suspend fun getTvShowById(id: Int): TvShow?
}