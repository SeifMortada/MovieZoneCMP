package com.gameZone.di

import com.gameZone.usecase.AddMovieToFavorites
import com.gameZone.usecase.AddTvShowToFavourites
import com.gameZone.usecase.RetrieveFavouriteMovies
import org.koin.dsl.module

val domainModule = module {
    factory { AddMovieToFavorites(get()) }
    factory { AddTvShowToFavourites(get()) }
    factory { RetrieveFavouriteMovies(get()) }

}