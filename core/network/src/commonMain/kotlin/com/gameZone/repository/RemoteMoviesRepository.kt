package com.gameZone.repository

import com.gameZone.api.mapper.toDomainMovie
import com.gameZone.api.service.ApiService
import com.gameZone.models.ApiOperation
import com.gameZone.models.Movie

class RemoteMoviesRepository(private val apiService: ApiService) :
    MoviesRepository {
    override suspend fun getPopularMovies(): ApiOperation<List<Movie>> {
        return when (val result = apiService.getPopularMovies()) {
            is ApiOperation.Success -> {
                ApiOperation.Success(result.data.results.map { it.toDomainMovie() })
            }
            is ApiOperation.Failure -> {
                ApiOperation.Failure(result.exception)
            }
        }
    }

}