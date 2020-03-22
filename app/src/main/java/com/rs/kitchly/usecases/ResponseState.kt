package com.rs.kitchly.usecases

sealed class ResponseState<out T>
data class NetworkError<Nothing>(val message: String?) : ResponseState<Nothing>()
object Loading : ResponseState<Nothing>()
data class Success<out T>(val data: T) : ResponseState<T>()