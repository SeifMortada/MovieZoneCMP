package org.example.moviezone.di


import com.gameZone.di.networkModule
import details.di.detailsModule
import home.di.homeModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import search.di.searchModule

fun initKoin(koinApplication: ((KoinApplication) -> Unit)? = null) {
    startKoin {
        modules(
            networkModule,
            homeModule,
            detailsModule,
            searchModule
        )
    }
}