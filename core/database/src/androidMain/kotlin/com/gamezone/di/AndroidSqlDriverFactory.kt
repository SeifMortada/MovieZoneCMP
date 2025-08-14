package com.gamezone.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.gamezone.db.MovieZoneDB

actual class SqlDriverFactory actual constructor(context: Any?) {
    private val context = context as Context

    actual fun getDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = MovieZoneDB.Schema,
            context = context,
            name = "moviezone.db"
        )
    }
}