package com.example.myapplication.presentation.loginscreen

sealed class LoginEvent{
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object SignInClicked : LoginEvent()
    object ErrorDismissed : LoginEvent()
}
