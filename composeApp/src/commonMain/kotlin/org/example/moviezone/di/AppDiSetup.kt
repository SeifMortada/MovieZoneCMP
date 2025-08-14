package org.example.moviezone.di


import com.gameZone.di.networkModule
import com.gamezone.di.coreDatabaseModule
import com.gamezone.di.databaseModule
import details.di.detailsModule
import home.di.homeModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import search.di.searchModule
fun initKoin(koinApplication: ((KoinApplication) -> Unit)? = null) {
    startKoin {
        koinApplication?.invoke(this)
        modules(
            networkModule,
            homeModule,
            detailsModule,
            coreDatabaseModule,
            databaseModule,
            searchModule
        )
    }
}