package com.gameZone.api.service

import com.gameZone.api.response.MoviesResponse
import com.gameZone.api.response.PopularTvShowsResponse
import com.gameZone.api.response.RemoteMovie
import com.gameZone.models.ApiOperation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class ApiService(private val httpClient: HttpClient) {

    suspend fun getPopularMovies(): ApiOperation<MoviesResponse> {
        return safeApiCall {
            httpClient.get("3/movie/popular")
                .body<MoviesResponse>()
        }
    }

    suspend fun getTopRatedMovies(): ApiOperation<MoviesResponse> {
        return safeApiCall {
            httpClient.get("3/movie/top_rated")
                .body<MoviesResponse>()
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

    suspend fun searchMovies(query: String): ApiOperation<MoviesResponse> {
        return safeApiCall {
            httpClient.get("3/search/movie") {
                url {
                    parameters.append("query", query)
                }
            }.body<MoviesResponse>()
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