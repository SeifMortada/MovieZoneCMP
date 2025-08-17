package com.gameZone.client

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual object KtorClient {
    val apiKey="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiMzM5ZmQ0MGE2MGYxNzkwNmMwYmI3MzYyNWJhNTJiYSIsIm5iZiI6MTc1MjI2MzY2My43MTYsInN1YiI6IjY4NzE2YmVmMmM0ZWZhY2JjMTU0MTc5ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.LXm2cpbNprWHmreWfJs-eDmXxJ3dgZMIJ0hDDzd2nq0"
    actual fun getInstance(): HttpClient {
        return HttpClient {
            defaultRequest {
                url {
                    host = "api.themoviedb.org"
                    protocol = URLProtocol.HTTPS
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }
                headers.append("Authorization", "Bearer $apiKey")
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 6000
                connectTimeoutMillis = 6000
                requestTimeoutMillis = 6000
            }
        }
    }
}
