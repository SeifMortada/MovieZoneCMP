package home.di

import home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule= module {
    viewModel { HomeViewModel(
        getPopularMoviesUseCase = get()
    ) }
}