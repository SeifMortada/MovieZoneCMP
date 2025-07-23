package org.example.moviezone

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform