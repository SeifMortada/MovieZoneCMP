package com.gameZone.api.service

import com.gameZone.api.response.PopularMoviesResponse
import com.gameZone.models.ApiOperation
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class ApiService (private val httpClient: HttpClient){

    suspend fun getPopularMovies(): ApiOperation<PopularMoviesResponse> {
        return safeApiCall {
            httpClient.get("3/movie/popular")
                .body<PopularMoviesResponse>()
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