package com.example.medico.utils

sealed class ResultState<out T> {
    data object Loading : ResultState<Nothing>()
    data object Idle : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val error: Throwable) : ResultState<Nothing>()
}