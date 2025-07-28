package com.gameZone.repository

import com.gameZone.api.mapper.toDomainTvShow
import com.gameZone.api.service.ApiService
import com.gameZone.models.ApiOperation
import com.gameZone.models.TvShow

class RemoteSeriesRepository(private val apiService: ApiService) : SeriesRepository {
    override suspend fun getPopularTvShows():  ApiOperation<List<TvShow>>{
        return when (val result = apiService.getPopularTvShows()) {
            is ApiOperation.Success -> {
                ApiOperation.Success(result.data.results.map { it.toDomainTvShow() })
            }
            is ApiOperation.Failure -> {
                ApiOperation.Failure(result.exception)
            }
        }
    }

}