package org.example.moviezone

import android.app.Application
import org.example.moviezone.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            it.modules(
                module {
                    single { this@BaseApplication.applicationContext }
                }
            )
        }
    }
}