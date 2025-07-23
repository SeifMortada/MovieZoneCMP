package org.example.moviezone.di

import com.gameZone.di.domainModule
import com.gameZone.di.networkModule
import home.di.homeModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(koinApplication: ((KoinApplication) -> Unit)? = null) {
    startKoin {
        modules(
            networkModule,
            domainModule,
            homeModule
        )
    }
}