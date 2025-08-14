package com.gamezone.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.gamezone.db.MovieZoneDB

actual class SqlDriverFactory actual constructor(context: Any?) {
    actual fun getDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:moviezone.db")
        MovieZoneDB.Schema.create(driver)
        return driver
    }
}