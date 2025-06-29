package com.example.myapplication.presentation.singup



sealed class SignUpEvent {
    data class EmailChanged(val email: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()
    data class PasswordConfirmationChanged(val password: String) : SignUpEvent()
    object SignUpClicked : SignUpEvent()
    object ErrorDismissed : SignUpEvent()
}