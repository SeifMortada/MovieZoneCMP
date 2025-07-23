package com.gameZone.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    const val TIME_OUT = 3000L
    private const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiMzM5ZmQ0MGE2MGYxNzkwNmMwYmI3MzYyNWJhNTJiYSIsIm5iZiI6MTc1MjI2MzY2My43MTYsInN1YiI6IjY4NzE2YmVmMmM0ZWZhY2JjMTU0MTc5ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.LXm2cpbNprWHmreWfJs-eDmXxJ3dgZMIJ0hDDzd2nq0"

    fun getInstance(): HttpClient {
        return HttpClient {
            defaultRequest {
                url {
                    host = "api.themoviedb.org"
                    protocol = URLProtocol.HTTPS
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }
                headers.append("Authorization", "Bearer $API_KEY")

            }
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }

            install(HttpTimeout) {
                socketTimeoutMillis = TIME_OUT
                connectTimeoutMillis = TIME_OUT
                requestTimeoutMillis = TIME_OUT
            }

        }
    }
}