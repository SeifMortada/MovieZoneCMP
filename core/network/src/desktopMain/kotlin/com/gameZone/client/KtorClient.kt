package com.gameZone.client

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate

actual object KtorClient {
    val apiKey="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiMzM5ZmQ0MGE2MGYxNzkwNmMwYmI3MzYyNWJhNTJiYSIsIm5iZiI6MTc1MjI2MzY2My43MTYsInN1YiI6IjY4NzE2YmVmMmM0ZWZhY2JjMTU0MTc5ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.LXm2cpbNprWHmreWfJs-eDmXxJ3dgZMIJ0hDDzd2nq0"
    actual fun getInstance(): HttpClient {
        return HttpClient(CIO) {
            engine {
                https {
                    trustManager = object : X509TrustManager {
                        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                    }
                }
            }

            defaultRequest {
                url {
                    host = "api.themoviedb.org"
                    protocol = URLProtocol.HTTPS
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                }
                headers.append("Authorization", "Bearer $apiKey") // keep or replace with your key management
            }

            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }

            install(HttpTimeout) {
                socketTimeoutMillis = 3000
                connectTimeoutMillis = 3000
                requestTimeoutMillis = 3000
            }
        }
    }
}
