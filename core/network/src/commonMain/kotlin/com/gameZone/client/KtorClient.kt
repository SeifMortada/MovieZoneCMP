package com.gameZone.client

import io.ktor.client.HttpClient

expect object KtorClient {
    fun getInstance(): HttpClient
}
