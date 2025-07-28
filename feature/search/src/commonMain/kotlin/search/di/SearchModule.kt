package search.di

import search.domain.SearchUseCase
import search.presentation.SearchViewModel
import org.koin.dsl.module

val searchModule = module {
    factory { SearchUseCase(get()) }
    factory { SearchViewModel(get()) }
}
