package com.gamezone.di

import app.cash.sqldelight.db.SqlDriver

expect class SqlDriverFactory(context: Any? = null) {
    fun getDriver(): SqlDriver
}