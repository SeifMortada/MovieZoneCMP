package home.di

import home.HomeViewModel
import com.gameZone.usecase.AddMovieToFavorites
import home.domain.usecase.GetPopularMoviesUseCase
import home.domain.usecase.GetPopularTvShowsUseCase
import home.domain.usecase.GetTopRatedMovies
import com.gameZone.usecase.AddTvShowToFavourites
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule= module {
    factory { GetPopularMoviesUseCase(repository = get()) }
    factory { GetTopRatedMovies(movieRepository = get()) }
    factory { GetPopularTvShowsUseCase(repository = get()) }
    factory { AddTvShowToFavourites(localDbRepository = get()) }
    factory { AddMovieToFavorites(localDbRepository = get()) }

    viewModel { HomeViewModel(
        getPopularMoviesUseCase = get(),
        getTopRatedMoviesUseCase = get(),
        getPopularTvShowsUseCase = get(),
        addTvShowToFavourites = get(),
        addMovieToFavorites = get()
    ) }
}