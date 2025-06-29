package com.example.myapplication.domain.useCases.auth_use_cases

import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.util.AuthResult

class SignIn(
    private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): AuthResult<Unit> {
        if (email.isBlank()) {
            return AuthResult.Error("Email can not be empty")
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return AuthResult.Error("Email format is invalid.")
        }
        if (password.isBlank()) {
            return AuthResult.Error("Password can not be empty.")
        }
        if (password.length < 6) {
            return AuthResult.Error("Password must be at least 6 characters long.")
        }
        return repository.signInWithEmailAndPassword(email, password)
    }
}