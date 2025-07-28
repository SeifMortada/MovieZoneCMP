package home.di

import home.HomeViewModel
import home.domain.usecase.GetPopularMoviesUseCase
import home.domain.usecase.GetPopularTvShowsUseCase
import home.domain.usecase.GetTopRatedMovies
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule= module {
    factory { GetPopularMoviesUseCase(repository = get()) }
    factory { GetTopRatedMovies(movieRepository = get()) }
    factory { GetPopularTvShowsUseCase(repository = get()) }

    viewModel { HomeViewModel(
        getPopularMoviesUseCase = get(),
        getTopRatedMoviesUseCase = get(),
        getPopularTvShowsUseCase = get(),
    ) }
}