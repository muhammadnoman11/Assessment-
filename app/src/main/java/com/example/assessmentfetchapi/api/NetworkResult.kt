package com.example.assessmentfetchapi.api

sealed class NetworkResult<T>(val body: T? = null, val message: String? = null) {

    class Success<T>(body: T?) : NetworkResult<T>(body)
    class Error<T>(message: String?, body: T? = null) : NetworkResult<T>(body, message)
    class Loading<T> : NetworkResult<T>()
}
