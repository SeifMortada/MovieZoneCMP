package com.gamezone.di

import app.cash.sqldelight.db.SqlDriver
import com.gamezone.db.MovieZoneDB
import org.koin.core.module.Module
import org.koin.dsl.module

actual val coreDatabaseModule: Module = module {
    single { SqlDriverFactory().getDriver() }
    single { MovieZoneDB.invoke(get<SqlDriver>()) }

}
