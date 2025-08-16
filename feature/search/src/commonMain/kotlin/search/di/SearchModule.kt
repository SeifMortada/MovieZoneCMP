package search.di

import org.koin.core.module.dsl.viewModel
import search.domain.SearchUseCase
import search.presentation.SearchViewModel
import org.koin.dsl.module

val searchModule = module {
    factory { SearchUseCase(get()) }
    viewModel { SearchViewModel(get()) }
}
