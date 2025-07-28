package com.gameZone.repository

import com.gameZone.models.ApiOperation
import com.gameZone.models.TvShow

interface SeriesRepository {
    suspend fun getPopularTvShows():  ApiOperation<List<TvShow>>
}