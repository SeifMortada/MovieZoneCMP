package com.gameZone.di

import com.gameZone.usecase.GetPopularMoviesUseCase
import org.koin.dsl.module

val domainModule= module {
    factory { GetPopularMoviesUseCase(repository = get()) }
}