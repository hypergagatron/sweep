package com.gaga.sweep.domain

sealed interface DataStatus<out T> {

    object Loading : DataStatus<Nothing>
    data class Success<out T>(val data: T? = null) : DataStatus<T>

    data class Failure<out T>(
        val exception: Throwable,
        val cachedData: T? = null
    ) : DataStatus<T>
}
