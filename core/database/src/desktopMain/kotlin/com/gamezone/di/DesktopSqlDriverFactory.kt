package com.gamezone.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

actual class SqlDriverFactory actual constructor(context: Any?) {
    actual fun getDriver(): SqlDriver {
       return JdbcSqliteDriver(
           "jdbc:sqlite:moviezone.db"
       )
    }
}