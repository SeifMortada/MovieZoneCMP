package com.gameZone.api.service

import com.gameZone.api.response.PopularMoviesResponse
import com.gameZone.api.response.PopularTvShowsResponse
import com.gameZone.api.response.RemoteMovie
import com.gameZone.models.ApiOperation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class ApiService(private val httpClient: HttpClient) {

    suspend fun getPopularMovies(): ApiOperation<PopularMoviesResponse> {
        return safeApiCall {
            httpClient.get("3/movie/popular")
                .body<PopularMoviesResponse>()
        }
    }

    suspend fun getTopRatedMovies(): ApiOperation<PopularMoviesResponse> {
        return safeApiCall {
            httpClient.get("3/movie/top_rated")
                .body<PopularMoviesResponse>()
        }
    }

    suspend fun getMovieDetails(movieId: Int): ApiOperation<RemoteMovie> {
        return safeApiCall {
            httpClient.get("3/movie/$movieId")
                .body<RemoteMovie>()
        }
    }

    suspend fun getPopularTvShows(): ApiOperation<PopularTvShowsResponse> {
        return safeApiCall {
            httpClient.get("3/tv/popular")
                .body<PopularTvShowsResponse>()
        }
    }
}

private inline fun <T> safeApiCall(apiCall: () -> T): ApiOperation<T> {
    return try {
        ApiOperation.Success(apiCall())
    } catch (e: Exception) {
        ApiOperation.Failure(e)
    }

}