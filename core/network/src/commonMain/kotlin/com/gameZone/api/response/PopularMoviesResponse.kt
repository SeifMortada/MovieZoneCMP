package com.gameZone.api.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularMoviesResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<RemoteMovie>
)