package details.di

import details.presentation.MovieDetailsViewModel
import details.domain.GetMovieDetailsUseCase
import org.koin.dsl.module

val detailsModule = module {
    factory { GetMovieDetailsUseCase(get()) }
    factory { MovieDetailsViewModel(get(), get(), get()) }
}
