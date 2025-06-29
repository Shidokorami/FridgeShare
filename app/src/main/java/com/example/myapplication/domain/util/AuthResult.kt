package com.example.myapplication.domain.util

sealed class AuthResult<out T> {
    data class Success<out T>(val data: T) : AuthResult<T>()

    data class Error(val message: String, val exception: Exception? = null) : AuthResult<Nothing>()

    object Loading : AuthResult<Nothing>()
}
