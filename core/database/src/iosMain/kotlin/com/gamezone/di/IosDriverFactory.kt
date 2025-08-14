package com.gamezone.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.gamezone.db.MovieZoneDB

actual class SqlDriverFactory actual constructor(context: Any?) {
    actual fun getDriver(): SqlDriver {
       return NativeSqliteDriver(
           MovieZoneDB.Schema,
           "moviezone.db"
       )
    }
}