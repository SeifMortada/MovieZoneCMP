package com.gameZone.models

sealed interface ApiOperation<T> {
    data class Success<T>(val data: T) : ApiOperation<T>
    data class Failure<T>(val exception: Exception) : ApiOperation<T>

    fun onSuccess(action: (T) -> Unit): ApiOperation<T> {
        if (this is Success) {
            action(data)
        }
        return this
    }

    fun onFailure(action: (Exception) -> Unit): ApiOperation<T> {
        if (this is Failure) {
            action(exception)
        }
        return this
    }
}