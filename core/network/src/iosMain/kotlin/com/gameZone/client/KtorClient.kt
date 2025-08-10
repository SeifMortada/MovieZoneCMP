package com.gameZone.client

import io.ktor.client.HttpClient

actual object KtorClient {
    val apiKey="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiMzM5ZmQ0MGE2MGYxNzkwNmMwYmI3MzYyNWJhNTJiYSIsIm5iZiI6MTc1MjI2MzY2My43MTYsInN1YiI6IjY4NzE2YmVmMmM0ZWZhY2JjMTU0MTc5ZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.LXm2cpbNprWHmreWfJs-eDmXxJ3dgZMIJ0hDDzd2nq0"
    actual fun getInstance(): HttpClient {
        return HttpClient()}
}