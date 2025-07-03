package com.example.myapplication.presentation.singup

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

