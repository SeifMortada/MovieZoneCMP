package com.gameZone.di

import com.gameZone.api.service.ApiService
import com.gameZone.client.KtorClient
import com.gameZone.repository.MoviesRepository
import com.gameZone.repository.RemoteMoviesRepository
import com.gameZone.repository.RemoteSeriesRepository
import com.gameZone.repository.SeriesRepository
import org.koin.dsl.module

val networkModule= module {
    single { KtorClient.getInstance() }
    single { ApiService(httpClient = get()) }
    factory<MoviesRepository> { RemoteMoviesRepository(apiService = get()) }
    factory<SeriesRepository> { RemoteSeriesRepository(apiService = get()) }
}