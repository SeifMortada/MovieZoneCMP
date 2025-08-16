package favourites.di

import favourites.presentation.FavouritesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favouritesModule = module {
    viewModel { FavouritesViewModel(get(),get()) }
}
