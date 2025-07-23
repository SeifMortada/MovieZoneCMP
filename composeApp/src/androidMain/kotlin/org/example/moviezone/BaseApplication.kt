package org.example.moviezone

import android.app.Application
import org.example.moviezone.di.initKoin

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
       initKoin()
    }
}