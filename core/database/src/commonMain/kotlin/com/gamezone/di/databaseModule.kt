package com.gamezone.di

import com.gameZone.repository.LocalDbRepository
import com.gamezone.db.MovieZoneDB
import com.gamezone.repo.LocalDbRepositoryImpl
import org.koin.dsl.module

expect val coreDatabaseModule: org.koin.core.module.Module

val databaseModule = module {
    factory<LocalDbRepository> { LocalDbRepositoryImpl(get<MovieZoneDB>()) }
}