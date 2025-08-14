package favourites.di

import favourites.presentation.FavouritesViewModel
import org.koin.dsl.module

val favouritesModule = module {
    factory { FavouritesViewModel(get()) }
}
